import { createContext } from 'react'

export const SwitcherContext = createContext({
    activeTabId: 1,
    setActiveTabId: (id: number) => {}
})
