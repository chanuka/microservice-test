// import React from 'react';
import React, { useRef } from 'react';
import NavBar from '../Components/NavBar';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import axios from 'axios';
import { useEffect } from 'react';

const StyledTableCell = styled(TableCell)(({ theme }) => ({
      [`&.${tableCellClasses.head}`]: {
            backgroundColor: theme.palette.common.black,
            color: theme.palette.common.white,
      },
      [`&.${tableCellClasses.body}`]: {
            fontSize: 14,
      },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
      '&:nth-of-type(odd)': {
            backgroundColor: theme.palette.action.hover,
      },
      // hide last border
      '&:last-child td, &:last-child th': {
            border: 0,
      },
}));

const NYT_API_KEY = '0KRF3LDaMioYZ3vC5s4orh5jSOjKOzgI';
const HomeScreen = () => {

      const initialized = useRef(false)

      console.log("Component rendered");
      const [data, setData] = React.useState([]);

      // useEffect(() => {
      //       if (!initialized.current) {
      //             initialized.current = true

      //        fetchData();
      //       console.log("Effect executed");
      //       }
      //       }, []);


            useEffect(() => {
            let messageCount = 0;
                  const eventSource = new EventSource('http://localhost:9191/router/customers/stream');
                  eventSource.onmessage = (event) => {
                    const newData = JSON.parse(event.data);

                    setData(prevData => [...prevData, newData]);
                    messageCount++;
                    if (messageCount === 12) {
                        eventSource.close();
                    }
                  };
              
                  return () => {
                    eventSource.close();
                  };
                }, []);


      // const fetchData = async () => {
      //       try {
      //             // const response = await axios.get('https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json', {
      //             // const response = await axios.get('http://localhost:9191/customers', {
      //             //       params: {
      //             //             // 'api-key': NYT_API_KEY
      //             //       }
      //             // });
      //             // setData(response.data);

      //             const eventSource = new EventSource('http://localhost:9191/customers/stream');
      //             eventSource.onmessage = (event) => {
      //               const newData = JSON.parse(event.data);
      //               setData(prevData => [...prevData, newData]);
      //             };
              
      //             return () => {
      //               eventSource.close();
      //             };


      //       } catch (error) {
      //             console.error('Error fetching books:', error);
      //       }
      // };

      console.log("madusns", data)

      return (
            <>
                  <div style={{ backgroundColor: '#bbbdbb' }}>
                        <NavBar />

                        <center>
                              <div style={{ maxWidth: '70%', marginTop: "30px" }}>
                                    <TableContainer component={Paper}>
                                          <Table sx={{ minWidth: 700 }} aria-label="customized table">
                                                <TableHead>
                                                      <TableRow>
                                                            <StyledTableCell>Rank</StyledTableCell>
                                                            <StyledTableCell align="left">Author</StyledTableCell>
                                                            <StyledTableCell align="left">Title</StyledTableCell>
                                                            <StyledTableCell align="left">Publisher</StyledTableCell>
                                                            <StyledTableCell align="left">Book Image</StyledTableCell>
                                                      </TableRow>
                                                </TableHead>
                                                <TableBody>
                                                      {data.map((row) => (
                                                            <StyledTableRow key={row.id}>
                                                                  <StyledTableCell component="th" scope="row">
                                                                        {row.id}
                                                                  </StyledTableCell>
                                                                  <StyledTableCell align="left">{row.name}</StyledTableCell>
                                                                  <StyledTableCell align="left">{row.name}</StyledTableCell>
                                                                  <StyledTableCell align="left">{row.name}</StyledTableCell>
                                                                  <StyledTableCell align="left"><img src={row.book_image} alt="Image" style={{ width: "50%", height: "100px" }} /></StyledTableCell>
                                                            </StyledTableRow>
                                                      ))}
                                                </TableBody>
                                          </Table>
                                    </TableContainer>
                              </div>
                        </center>
                  </div>
            </>
      )
}

export default HomeScreen