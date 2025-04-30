"use client";
import React, { useEffect, useState } from "react";
import styles from "./PropertyGrid.module.css";
import PropertyCard from "./PropertyCard";
import Filters from "./Filters";
import { fetchAds } from "../api/adApi";


const PropertyGrid = () => {
    const [ads, setAds] = useState([]);
    const [filters, setFilters] = useState({});
    const [page, setPage] = useState(0);
    const limit = 12;

    let user = null;

    try {
        const storedUser = localStorage.getItem("supabase_user");
        if (storedUser && storedUser !== "undefined") {
            user = JSON.parse(storedUser);
        }
    } catch (err) {
        console.error("Error parsing user from localStorage:", err);
    }


    const loadAds = async (pageToLoad, filtersToUse) => {
        const fetched = await fetchAds(pageToLoad, limit, filtersToUse);
        setAds(fetched);
    };


    useEffect(() => {
        setPage(0);
        loadAds(0, filters);
    }, [filters]);

    useEffect(() => {
        loadAds(page, filters);
    }, [page]);

    const handleFiltersChange = (newFilters) => {
        setFilters(newFilters);
    };
    console.log(user);

    return (
        <>
            {user ? (
                <Filters onFiltersChange={handleFiltersChange} />
            ) : (
                <div style={{ padding: "1rem", textAlign: "center", backgroundColor: "#f8d7da", color: "#721c24", borderRadius: "8px", margin: "1rem" }}>
                    <strong>Log in to filter the ads.</strong>
                </div>
            )}

            {ads.length === 0 ? (
                <div>No ads found.</div>
            ) : (
                <>
                    <section className={styles.propertyGrid}>
                        {ads.map((ad) => (
                            <PropertyCard key={ad.id} link={ad.url} {...ad} />
                        ))}
                    </section>
                    <div style={{ marginTop: "20px", textAlign: "center" }}>
                        <button onClick={() => setPage(p => Math.max(p - 1, 0))} disabled={page === 0}>Previous</button>
                        <span style={{ margin: "0 10px" }}>Page {page + 1}</span>
                        <button onClick={() => setPage(p => p + 1)}>Next</button>
                    </div>
                </>
            )}
        </>
    );
};

export default PropertyGrid;
