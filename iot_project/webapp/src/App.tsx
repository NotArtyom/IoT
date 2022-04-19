import React from 'react'
import './App.css'
import { Header } from 'components/header'

function App() {

    const onButtonClick = () => {}

    return (
        <div className="App">
            <Header onClick={onButtonClick}/>
        </div>
    )
}

export default App
