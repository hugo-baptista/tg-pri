import React, { useState } from 'react';

function TermoPesquisa() {
    const [inputValue, setInputValue] = useState('');
  
    const handleSubmit = (event) => {
      event.preventDefault(); // prevent form submission
      // Do something with the input value, e.g. submit it to a server
      console.log('Termo submetido:', inputValue);
      // Clear the input box
      setInputValue('');
    };
  
    const handleInputChange = (event) => {
      setInputValue(event.target.value);
    };
  
    return (
      <div className='TermoPesquisa'>
      <form onSubmit={handleSubmit}>
        <label>
          Introduza um Termo:
          <input type="text" value={inputValue} onChange={handleInputChange} />
        </label>
        <button type="submit">Pesquisar</button>
      </form>
      </div>
    );
  }
  
  export default TermoPesquisa;