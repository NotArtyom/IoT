import './Container.styles.css'
import { EButtonState } from '../button/Button.types'
import React, { FC, useContext } from 'react'
import { AppContext } from 'App.context'
import { IContainerProps } from './Container.types'

export const Container: FC<IContainerProps> = ({response}) => {
    const {buttonState} = useContext(AppContext)

    console.log('ids', response && response.ids)
    const renderInnerContent = (state: EButtonState) => {
        switch (state) {
            case EButtonState.Check:
                return <img className="Container-pic" src={require('assets/Click.png')}/>
            case EButtonState.CheckDisabled:
                return <CheckDisabled/>
            case EButtonState.Register:
                return <img className="Container-pic" src={require('assets/NoRecords.png')}/>
        }
    }

    return (
        <div className="Container-container">
            {!response && renderInnerContent(buttonState)}
            <div className="Container-check">
                {(response && response.ids) && checking(response)}
                {<div className="Container-registering">
                    {response && response.ids && response.ids.map(id => {
                        return (<div key={id} className="Container-registering-item">{id}</div>)
                    })}
                </div>}
            </div>
        </div>
    )
}

const checking = (response: IResponse) => {
    if (response.success) {
        return (<Correct/>)
    } else {
        return (<Incorrect/>)
    }
}

const Correct = () => (
    <svg width="41" height="40" viewBox="0 0 41 40" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path
            d="M15.5 26.95L9.71665 21.1666C9.06665 20.5166 8.01665 20.5166 7.36665 21.1666C6.71665 21.8166 6.71665 22.8666 7.36665 23.5166L14.3333 30.4833C14.9833 31.1333 16.0333 31.1333 16.6833 30.4833L34.3167 12.85C34.9667 12.2 34.9667 11.15 34.3167 10.5C33.6667 9.84995 32.6166 9.84995 31.9666 10.5L15.5 26.95Z"
            fill="#3CBC58"/>
    </svg>
)

const Incorrect = () => (
    <svg width="41" height="40" viewBox="0 0 41 40" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path
            d="M20.5 3.33325C11.3 3.33325 3.83337 10.7999 3.83337 19.9999C3.83337 29.1999 11.3 36.6666 20.5 36.6666C29.7 36.6666 37.1667 29.1999 37.1667 19.9999C37.1667 10.7999 29.7 3.33325 20.5 3.33325ZM7.16671 19.9999C7.16671 12.6333 13.1334 6.66659 20.5 6.66659C23.5834 6.66659 26.4167 7.71659 28.6667 9.48325L9.98337 28.1666C8.21671 25.9166 7.16671 23.0833 7.16671 19.9999ZM20.5 33.3333C17.4167 33.3333 14.5834 32.2833 12.3334 30.5166L31.0167 11.8333C32.7834 14.0833 33.8334 16.9166 33.8334 19.9999C33.8334 27.3666 27.8667 33.3333 20.5 33.3333Z"
            fill="#FF5959"/>
    </svg>
)

const CheckDisabled = () => {
    return <img className="Container-pic" src={require('assets/Tag.png')}/>
}
