import { FC, useCallback } from 'react'
import { ButtonProps, EButtonState } from './Button.types'
import './Button.styles.css'

export const Button: FC<ButtonProps> = ({
                                            state,
                                            onClick
                                        }) => {
    const isActive = state === EButtonState.Check || state === EButtonState.Register
    const containerStyle = useCallback(() => isActive ?
        'Button-container-active' : 'Button-container-disabled', [isActive])
    const textStyle = useCallback(() => isActive ? 'Button-text-active' : 'Button-text-disabled', [isActive])

    const text = () => {
        switch (state) {
            case EButtonState.Check:
                return 'Проверить UUID'
            case EButtonState.CheckDisabled:
                return 'Считывание'
            case EButtonState.Register:
                return 'Записать новый UUID'
            case EButtonState.Registering:
                return 'Считывание'
        }
    }

    return (
        <div onClick={onClick} className={`Button-container ${containerStyle()}`}>
            <div className={`Button-text ${textStyle()}`}>{text()}</div>
        </div>
    )
}
