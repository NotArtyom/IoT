import React, { useEffect, useRef, useState } from 'react'
import './App.css'
import { Header } from 'components/header'
import { Container } from 'components/container'
import { AppContext } from './App.context'
import { EButtonState } from './components/button/Button.types'
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'

function App() {
    const [buttonState, setButtonState] = useState<EButtonState>(EButtonState.Register)
    const socket = useRef(new SockJS('http://172.20.10.2:8080/ws-endpoint'))
    const stompClient = useRef(Stomp.over(socket.current))
    const [responseData, setResponseData] = useState<IResponse | null>(null)

    useEffect(() => {
        stompClient.current.connect({}, function (frame) {
            stompClient.current.subscribe('/topic/updates', function (update) {
                setResponseData(JSON.parse(update.body))
            })
        })
    }, [buttonState])

    useEffect(() => {
        if (buttonState === EButtonState.CheckDisabled) stompClient.current.send('/action', {}, JSON.stringify({'action': 'CHECK'}))
        if (buttonState === EButtonState.Registering) stompClient.current.send('/action', {}, JSON.stringify({'action': 'REGISTER'}))
    }, [buttonState])

    return (
        <AppContext.Provider value={{buttonState, setButtonState}}>
            <div className="App">
                    <Header response={responseData}/>
                <Container response={responseData}/>
            </div>
        </AppContext.Provider>
    )
}

export default App
