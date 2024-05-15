import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import HomeScreen from './Screens/HomeScreen';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<HomeScreen />} />

      </Routes>
    </BrowserRouter>

  );
}

export default App;
