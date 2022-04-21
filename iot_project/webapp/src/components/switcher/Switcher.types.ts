import { TabProps } from 'components/tab/Tab.types'

export interface SwitcherProps {
    tabs: TabProps[]
}

export enum ESwitcher {
    Register = 1,
    Check = 2
}
