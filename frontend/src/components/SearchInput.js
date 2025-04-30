"use client";
import * as React from "react";
import styles from "./SearchInput.module.css";
import SearchIcon from "./SearchIcon";

const SearchInput = ({name,value,placeholder,onChange}) => {
    return (
        <form className={styles.searchContainer}>
            <label htmlFor="search" className={styles.searchLabel}>
                {placeholder}
            </label>
            <div className={styles.inputWrapper}>
                <input
                    type="search"
                    className={styles.searchInput}
                    value={value}
                    name={name}
                    placeholder={placeholder}
                    aria-label={placeholder}
                    onChange={onChange}
                />
                <SearchIcon />
            </div>
        </form>
    );
};

export default SearchInput;
