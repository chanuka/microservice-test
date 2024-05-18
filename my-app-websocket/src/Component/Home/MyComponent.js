// import React, { useState, useEffect } from 'react';
// import * as Stomp from 'stompjs';

// const MyComponent = () => {
//   const [connected, setConnected] = useState(false);
//   const [message, setMessage] = useState('');

//   useEffect(() => {
//     const connect = () => {
//       const ws = new WebSocket('ws://localhost:8080/rule-engine/register'); 
//       const client = Stomp.over(ws);
//       client.connect({}, () => {
//         setConnected(true);
//         client.subscribe('/queue/messages', (message) => {
//           setMessage(message.body);
//         });
//       });
//     };

//     connect();
//   }, []);

//   return (
//     <div>
//       {connected ? (
//         <div>Connected! Message: {message}</div>
//       ) : (
//         <div>Connecting...</div>
//       )}
//     </div>
//   );
// };

// export default MyComponent;
