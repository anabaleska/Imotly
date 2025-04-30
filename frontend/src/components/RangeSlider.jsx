"use client";
import React from "react";
import styles from "./RangeSlider.module.css";

const RangeSlider = ({ name, label, value, min, max, onChange }) => {
    return (
        <div className={styles.rangeSliderWrapper}>
            <label htmlFor={name}>{label}: {value}</label>
            <input
                type="range"
                id={name}
                name={name}
                min={min}
                max={max}
                value={value}
                onChange={onChange}
                className={styles.sliderInput}
            />
        </div>
    );
};

export default RangeSlider;
