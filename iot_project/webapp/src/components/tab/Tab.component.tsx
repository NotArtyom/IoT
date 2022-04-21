import React, { FC } from 'react'
import './Tab.styles.css'
import { TabPropsPrivate } from './Tab.types'

export const Tab: FC<TabPropsPrivate> = ({
                                      handleTabChange,
                                      isActive,
                                      text,
                                      id
                                  }) => (
    <div onClick={() => handleTabChange(id)} className={`Tab-container ${isActive && 'Tab-container-active'}`}>
        <div className={`Tab-text ${isActive && 'Tab-text-active'}`}>
            {text}
        </div>
    </div>
)
