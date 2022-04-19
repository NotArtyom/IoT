import React, { FC, useCallback, useEffect, useState } from 'react'
import { SwitcherContext } from '../switcher/Switcher.context'
import { Switcher } from 'components/switcher'
import { TABS } from './constants'
import './Header.styles.css'
import { Button } from '../button'
import { EButtonState } from '../button/Button.types'
import { HeaderProps } from './Header.types'
import { ESwitcher } from '../switcher/Switcher.types'
import { usePrevious } from '../../usePrevious'

export const Header: FC<HeaderProps> = ({
                                            onClick
                                        }) => {
    const [activeTabId, setActiveTabId] = useState<ESwitcher>(ESwitcher.Check)
    const [buttonState, setButtonState] = useState<EButtonState>(EButtonState.Register)
    const activeTabPrev = usePrevious(activeTabId)

    const onButtonClick = useCallback(() => {
        if (buttonState === EButtonState.Register) {
            setButtonState(EButtonState.Registering)
        } else {
            if (buttonState === EButtonState.Registering)
                setButtonState(EButtonState.Register)
            else {
                if (buttonState === EButtonState.CheckDisabled)
                    setButtonState(EButtonState.Check)
                else {
                    setButtonState(EButtonState.CheckDisabled)
                }
            }
        }
        onClick()
    }, [buttonState, onClick])

    useEffect(() => {
        if (!!activeTabPrev && activeTabPrev !== activeTabId) {
            if (activeTabId === ESwitcher.Check) {
                setButtonState(EButtonState.Check)
            } else {
                setButtonState(EButtonState.Register)
            }
        }
    },[activeTabId, activeTabPrev])

    return (
        <SwitcherContext.Provider value={{activeTabId, setActiveTabId}}>
            <div className="Header-container">
                <div className="Header-switcher">
                    <Switcher tabs={TABS}/>
                </div>
                <div className="Header-button">
                    <Button state={buttonState} onClick={onButtonClick}/>
                </div>
            </div>
        </SwitcherContext.Provider>
    )
}
