"use client";
import React from "react";
import styles from "./Form.module.css";


const SearchTypeToggle = () => {
    return (
        <section className={styles.toggleSection}>
            <div className={styles.toggleContainer}>
                <button className={styles.forSale}>For Sale</button>
                <button className={styles.forRent}>For Rent</button>
            </div>
            <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/a653b37ae8a8196d1b5c03f15192cd7eafa60cb9?placeholderIfAbsent=true&apiKey=b5449d56610e47ad8c1812a0efbd1ee6" alt="Divider" className={styles.img} />
        </section>
    );
};

export default SearchTypeToggle;