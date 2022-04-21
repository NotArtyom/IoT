import React, { FC, useCallback, useContext, useEffect, useState } from 'react'
import { Switcher, SwitcherContext } from 'components/switcher'
import { TABS } from './constants'
import './Header.styles.css'
import { Button } from '../button'
import { EButtonState } from '../button/Button.types'
import { HeaderProps } from './Header.types'
import { ESwitcher } from '../switcher/Switcher.types'
import { usePrevious } from 'usePrevious'
import { AppContext } from 'App.context'

export const Header: FC<HeaderProps> = ({response}) => {
    const context = useContext(AppContext)


    const [activeTabId, setActiveTabId] = useState<ESwitcher>(ESwitcher.Check)
    const [buttonState, setButtonState] = useState<EButtonState>(EButtonState.Register)
    const activeTabPrev = usePrevious(activeTabId)
    const responsePrev = usePrevious(response)

    useEffect(() => {
        if (response !== responsePrev) {
            if (buttonState === EButtonState.Registering) setButtonState(EButtonState.Register)
            if (buttonState === EButtonState.CheckDisabled) setButtonState(EButtonState.Check)
        }
    },[buttonState, response, responsePrev])

    const setButtonContextState = useCallback((state: EButtonState) => {
        context.setButtonState(state)
        setButtonState(state)
    },[context])

    const onButtonClick = useCallback(() => {
        if (buttonState === EButtonState.Register) {
            setButtonContextState(EButtonState.Registering)
        } else {
            if (buttonState === EButtonState.Registering)
                setButtonContextState(EButtonState.Register)
            else {
                if (buttonState === EButtonState.CheckDisabled)
                    setButtonContextState(EButtonState.Check)
                else {
                    setButtonContextState(EButtonState.CheckDisabled)
                }
            }
        }
    }, [buttonState, setButtonContextState])

    useEffect(() => {
        if (!!activeTabPrev && activeTabPrev !== activeTabId) {
            if (activeTabId === ESwitcher.Check) {
                setButtonContextState(EButtonState.Check)
            } else {
                setButtonContextState(EButtonState.Register)
            }
        }
    },[activeTabId, activeTabPrev, setButtonContextState])

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
