"use client";
import * as React from "react";
import styles from "./InputDesign.module.css";
import NavigationMenu from "./NavigationMenu";
import Logo from "./Logo";
import { Link, useNavigate } from "react-router-dom";

function InputDesign() {
    const navigate = useNavigate();
    let user = null;

    try {
        const storedUser = localStorage.getItem("supabase_user");
        if (storedUser && storedUser !== "undefined") {
            user = JSON.parse(storedUser);
        }
    } catch (err) {
        console.error("Error parsing user from localStorage:", err);
    }

    const handleLogout = () => {
        localStorage.removeItem("supabase_user");
        navigate("/login");
    };

    return (
        <header className={styles.header}>
            <NavigationMenu />
            <Logo />
            <div className={styles.userActions}>
                {user ? (
                    <>  <Link to="/" className={styles.loginButton}>
                        Saved Ads
                    </Link>
                        <span className={styles.userEmail}>Welcome, {user.email}</span>
                        <button onClick={handleLogout} className={styles.loginButton}>
                            Logout
                        </button>
                    </>
                ) : (
                    <>

                        <Link to="/login" className={styles.loginButton}>
                            Login
                        </Link>
                        <Link to="/register" className={styles.addListingButton}>
                            Register
                        </Link>
                    </>
                )}
            </div>
        </header>
    );
}

export default InputDesign;
