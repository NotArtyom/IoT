import { createContext } from 'react'
import { EButtonState } from './components/button/Button.types'

export const AppContext = createContext({
    buttonState : EButtonState.Register,
    setButtonState : (state: EButtonState) => {}
})
