export interface ButtonProps {
    state: EButtonState
    onClick(): void
}

export enum EButtonState {
    Register = 'Register',
    Registering = 'Registering',
    Check = 'Check',
    CheckDisabled = 'CheckDisabled'
}
