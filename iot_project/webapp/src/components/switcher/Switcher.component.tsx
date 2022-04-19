import React, { FC, useCallback, useContext, useState } from 'react'
import './Switcher.styles.css'
import { Tab } from '../tab'
import { ESwitcher, SwitcherProps } from './Switcher.types'
import { SwitcherContext } from './Switcher.context'

export const Switcher: FC<SwitcherProps> = ({
                                                tabs
                                            }) => {

    const [activeTab, setActiveTab] = useState<ESwitcher>(ESwitcher.Register)
    const { setActiveTabId } = useContext(SwitcherContext)
    const onClick = useCallback((id: number) => { return setActiveTabId(id), setActiveTab(id) }, [setActiveTabId])
    return (
            <div className="Switcher-container">
                {tabs.map((tab) => {
                        const isActive = tab.id === activeTab
                        return (<Tab {...tab} key={tab.id} handleTabChange={onClick} isActive={isActive}/>)
                    }
                )}
            </div>
    )
}
