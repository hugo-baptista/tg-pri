import Navbar from "./Navbar";

function App() {
  return (
    <div className="App">
      <Navbar/>
      <center>
        <header className="App-header">
          <p>Trabalho aqui</p>
          <button style={{
            height:"600px",
            width:"1000px",
            backgroundColor:"pink",
            fontStyle:"oblique",
            fontSize:"50px",
            borderColor:"purple",
            textDecorationColor:"purple"
          }}>Butón májico</button>
        </header>
      </center>
    </div>
  );
}

export default App;
