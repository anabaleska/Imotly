"use client";

import React, { useState } from "react";
import styles from "./DropDown.module.css";

import ChevronIcon from "./icons/ChevronIcon";

const DropDown = ({ name, value, onChange, options = [], label = "" }) => {
    const [isOpen, setIsOpen] = useState(false);

    const handleToggle = () => setIsOpen(!isOpen);

    const handleSelect = (option) => {
        onChange({
            target: {
                name,
                value: option
            }
        });
        setIsOpen(false);
    };

    return (
        <div className={styles.container} style={{width:'20%'}}>
            <label className={styles.label}>{label}</label>
            <button
                type="button"
                className={styles.selectButton}
                onClick={handleToggle}
                aria-haspopup="listbox"
                aria-expanded={isOpen}
            >
                <span className={styles.selectedValue}>
                    {value || ` ${label}`}
                </span>
                <ChevronIcon />
            </button>

            {isOpen && (
                <ul className={styles.dropdownList} role="listbox">
                    {options.map((option, index) => (
                        <li
                            key={index}
                            className={styles.dropdownItem}
                            role="option"
                            onClick={() => handleSelect(option)}
                        >
                            {option}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default DropDown;
