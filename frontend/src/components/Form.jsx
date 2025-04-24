import React from "react";
import styles from "./Form.module.css";
import SearchTypeToggle from "./SearchTypeToggle";
import SearchInput from "./SearchInput";
import PropertySelector from "./PropertySelector";
import RoomSelector from "./RoomSelector";

function Form() {
    return (
        <form className={styles.form}>
            <div className={styles.div}>
                <div className={styles.div2}>
                    <SearchTypeToggle />
                </div>
                <div className={styles.div4}>
                    <SearchInput />
                    <PropertySelector />
                    <RoomSelector />
                    <button type="button" className={styles.advanceSearch}>
                        Advance Search
                    </button>
                    <button type="submit" className={styles.search}>
                        Search
                    </button>
                </div>
            </div>
        </form>
    );
}

export default Form;