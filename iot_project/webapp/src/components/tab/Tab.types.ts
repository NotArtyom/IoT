export interface TabPropsPrivate extends TabProps{
    isActive: boolean,
    handleTabChange(id: number): void
}

export interface TabProps {
    id: number
    text: string
}
