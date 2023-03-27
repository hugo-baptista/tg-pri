const Navbar = () => {
    return (
        <div className="navbar">
            <h1>Menu Engenho de Pesquisa</h1>
            <div className="links">
                <a href="/">Página principal</a>
                <a href="/termo">Pesquisa por termo</a>
                <a href="/questao">Pesquisa por questão</a>
            </div>
        </div>
    );
}
 
export default Navbar;