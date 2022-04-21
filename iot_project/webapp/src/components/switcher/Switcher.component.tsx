import React, { FC, useCallback, useContext } from 'react'
import './Switcher.styles.css'
import { Tab } from '../tab'
import { SwitcherProps } from './Switcher.types'
import { SwitcherContext } from './Switcher.context'

export const Switcher: FC<SwitcherProps> = ({
                                                tabs
                                            }) => {

    const { activeTabId, setActiveTabId } = useContext(SwitcherContext)
    const onClick = useCallback((id: number) => { return setActiveTabId(id) }, [setActiveTabId])
    return (
            <div className="Switcher-container">
                {tabs.map((tab) => {
                        const isActive = tab.id === activeTabId
                        return (<Tab {...tab} key={tab.id} handleTabChange={onClick} isActive={isActive}/>)
                    }
                )}
            </div>
    )
}
