import { createContext } from 'react'

export const SwitcherContext = createContext({
    activeTabId: 0,
    setActiveTabId: (id: number) => {}
})
