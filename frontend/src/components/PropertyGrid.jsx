"use client";
import React, { useEffect, useState } from "react";
import styles from "./PropertyGrid.module.css";
import PropertyCard from "./PropertyCard";
import { fetchAds } from "../api/adApi";

const PropertyGrid = () => {
    const [ads, setAds] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [page, setPage] = useState(0);
    const limit = 10;

    useEffect(() => {
        const getAds = async () => {
            try {
                setLoading(true);
                const data = await fetchAds(page, limit);
                setAds(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        getAds();
    }, [page]);

    const handlePrev = () => page > 0 && setPage(page - 1);
    const handleNext = () => setPage(page + 1);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;

    return (
        <>
            <section className={styles.propertyGrid}>
                {ads.map((ad) => (
                    <PropertyCard key={ad.id} link={ad.url} {...ad} />
                ))}
            </section>
            <div style={{ marginTop: "20px", textAlign: "center" }}>
                <button onClick={handlePrev} disabled={page === 0}>Previous</button>
                <span style={{ margin: "0 10px" }}>Page {page + 1}</span>
                <button onClick={handleNext}>Next</button>
            </div>
        </>
    );
};

export default PropertyGrid;
