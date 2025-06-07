"use client";
import React, { useEffect, useState } from "react";
import {deleteSavedSearch, fetchSavedSearches} from "../api/adApi";
import styles from "./table.module.css";


const SavedSearches = () => {
    const [savedSearches, setSavedSearches] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [user, setUser] = useState(null);


    useEffect(() => {
        try {
            const storedUser = localStorage.getItem("supabase_user");
            if (storedUser && storedUser !== "undefined") {
                setUser(JSON.parse(storedUser));
            }
        } catch (err) {
            console.error("Error parsing user from localStorage:", err);
        }
    }, []);

    useEffect(() => {
        if (user) {
            fetchSavedSearches(user.email)
                .then(data => {
                    setSavedSearches(data);
                    setLoading(false);
                })
                .catch(err => {
                    setError("Failed to load saved searches");
                    setLoading(false);
                });
        }
    }, [user]);

    const handleRemove = async (id) => {

       const filtered = savedSearches.filter((search) => search.id !== id);
       setSavedSearches(filtered);

        const success = await deleteSavedSearch(id);

        if (!success) {
            setError("Failed to remove saved search. Please try again.");
            setSavedSearches(savedSearches);
        }
    };

    if (!user) return <div className={styles.text}>Please log in to view saved searches.</div>;
    if (loading) return <div className={styles.text}>Loading saved searches...</div>;
    if (error) return <div className={styles.text}>{error}</div>;
    console.log(savedSearches);

    return (
        <div style={{ padding: "1rem" }}>
            <h1 style={{color:"#3a0ca3", fontWeight:"lighter", textAlign:"center"}}>Saved Searches</h1>
            {savedSearches.length === 0 ? (
                <p>You have no saved searches.</p>
            ) : (
                <table className={styles.table}>
                    <thead className={styles.thead}>
                    <tr>
                        <th className={styles.th}>Location</th>
                        <th className={styles.th}>Heating</th>
                        <th className={styles.th}>Terrace</th>
                        <th className={styles.th}>Parking</th>
                        <th className={styles.th}>Furnished</th>
                        <th className={styles.th}>Basement</th>
                        <th className={styles.th}>Duplex</th>
                        <th className={styles.th}>Renovated</th>
                        <th className={styles.th}>Lift</th>
                        <th className={styles.th}>State</th>
                        <th className={styles.th}>Type</th>
                        <th className={styles.th}>For Sale</th>
                        <th className={styles.th}>New Building</th>
                        <th className={styles.th}>Actions</th>
                    </tr>
                    </thead>
                    <tbody className={styles.tbody}>
                    {savedSearches.map((search, index) => (
                        <tr key={index}>

                            <td className={styles.td}>{search.location || "-"}</td>
                            <td className={styles.td}>{search.heating || "-"}</td>
                            <td className={styles.td}>{search.terrace ? "Yes" : "No"}</td>
                            <td className={styles.td}>{search.parking ? "Yes" : "No"}</td>
                            <td className={styles.td}>{search.furnished ? "Yes" : "No"}</td>
                            <td className={styles.td}>{search.basement ? "Yes" : "No"}</td>
                            <td className={styles.td}>{search.duplex ? "Yes" : "No"}</td>
                            <td className={styles.td}>{search.renovated ? "Yes" : "No"}</td>
                            <td className={styles.td}>{search.lift ? "Yes" : "No"}</td>
                            <td className={styles.td}>{search.state || "-"}</td>
                            <td className={styles.td}>{search.type_of_obj || "-"}</td>
                            <td className={styles.td}>{search.for_sale ? "Yes" : "No"}</td>
                            <td className={styles.td}>{search.new_building ? "Yes" : "No"}</td>
                            <td className={styles.td}>
                                <button className={styles.removeButton}
                                        onClick={() => handleRemove(search.id)}
                                >Remove</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default SavedSearches;
