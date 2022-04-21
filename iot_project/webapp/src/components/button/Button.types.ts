export interface ButtonProps {
    state: EButtonState
    onClick(): void
}

export enum EButtonState {
    Register = 'REGISTER',
    Registering = 'REGISTERING',
    Check = 'CHECK',
    CheckDisabled = 'CHECKDISABLED'
}
