import React from "react";
import styles from "./Form.module.css";
import SearchTypeToggle from "./SearchTypeToggle";
import SearchInput from "./SearchInput";
function Form() {
    return (
        <form className={styles.form}>
            <div className={styles.div}>
                <div className={styles.div2}>
                    <SearchTypeToggle />
                </div>
                <div className={styles.div4}>
                    <SearchInput />
                    <SearchInput />
                    <button type="submit" className={styles.search}>
                        Search
                    </button>
                </div>
            </div>
        </form>
    );
}

export default Form;