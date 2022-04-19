import { createContext } from 'react'
import { EButtonState } from '../button/Button.types'

export const HeaderContext = createContext({
    buttonState : EButtonState.Check,
    setButtonState : (state: EButtonState) => {}
})
