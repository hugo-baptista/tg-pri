import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './index.css';
import Home from './Home';
import TermoPesquisa from './TermoPesquisa';
import Navbar from './Navbar';
import QuestaoPesquisa from './QuestaoPesquisa';

function App() {
  return (
    <div className="App">
        <Routes>
      <Route path="/" element={<Home />}>
        <Route exact path="/termo" component={TermoPesquisa} />
        <Route path="/questao" element={<QuestaoPesquisa />} />
        </Route>
        </Routes>
    </div>
      
  );
}

export default App;