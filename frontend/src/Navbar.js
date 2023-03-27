import React from "react";
import { BrowserRouter, Route, Link } from "react-router-dom";

const Navbar = () => {
    return (
        <div className="navbar">
            <h1>Serviço de Pesquisa</h1>
            <div className="links">
                <a><Link to="/">Página principal</Link></a>
                <a><Link to="/termo">Pesquisa por termo</Link></a>
                <a><Link to="/questao">Pesquisa por questão</Link></a>

            </div>
        </div>
    );
}
 
export default Navbar;