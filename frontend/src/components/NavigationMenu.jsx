import React from "react";
import styles from "./InputDesign.module.css";
import {Link} from "react-router-dom";

const NavigationMenu = () => {
    return (
        <nav className={styles.navigationMenu}>
            <Link to="/" className={styles.navItem}>Home</Link>
        </nav>
    );
};

export default NavigationMenu;
