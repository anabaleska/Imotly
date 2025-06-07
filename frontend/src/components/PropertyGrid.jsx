"use client";
import React, { useEffect, useState } from "react";
import styles from "./PropertyGrid.module.css";
import PropertyCard from "./PropertyCard";
import Filters from "./Filters";
import { fetchAds } from "../api/adApi";
import {Link} from "react-router-dom";


const PropertyGrid = () => {
    const [ads, setAds] = useState([]);
    const [filters, setFilters] = useState({});
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
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
        const {ads, totalPages} = await fetchAds(pageToLoad, limit, filtersToUse);

        setAds(ads);
        setTotalPages(totalPages);
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

    const renderPagination = () => {
        if (totalPages <= 1) return null;

        const pageNumbers = [];

        if (totalPages <= 7) {
            for (let i = 0; i < totalPages; i++) pageNumbers.push(i);
        } else {
            if (page <= 2) {
                pageNumbers.push(0, 1, 2, 3, -1, totalPages - 1);
            } else if (page >= totalPages - 3) {
                pageNumbers.push(0, -1, totalPages - 4, totalPages - 3, totalPages - 2, totalPages - 1);
            } else {
                pageNumbers.push(0, -1, page - 1, page, page + 1, -2, totalPages - 1);
            }
        }

        return (
            <div style={{ marginTop: "20px", textAlign: "center" }}>
                <button
                    onClick={() => setPage(p => Math.max(p - 1, 0))}
                    disabled={page === 0}
                    style={{
                        width:"80px",
                        borderRadius: "50px",
                        padding: "5px",
                        margin: "0 20px",
                        borderColor:"#6a6dcd",


                    }}
                >
                    Previous
                </button>

                {pageNumbers.map((p, idx) =>
                    p === -1 || p === -2 ? (
                        <span key={idx} style={{ margin: "0 5px" }}>...</span>
                    ) : (
                        <button
                            key={p}
                            onClick={() => setPage(p)}
                            style={{
                                width:"35px",
                                height:"35px",
                                borderRadius: "100px",
                                borderColor:"#2a2a55",
                                padding: "5px",
                                margin: "0 5px",
                                color: p===page ? "white" : "black",
                                fontWeight: p === page ? "bold" : "normal",
                                backgroundColor: p === page ? "#afaff6" : "transparent"
                            }}
                        >
                            {p + 1}
                        </button>
                    )
                )}

                <button
                    onClick={() => setPage(p => Math.min(p + 1, totalPages - 1))}
                    disabled={page === totalPages - 1}
                    style={{
                        width:"80px",
                        borderRadius: "50px",
                        padding: "5px",
                        margin: "0 20px",
                        borderColor:"#6a6dcd",

                    }}
                >
                    Next
                </button>
            </div>
        );
    };


console.log(user);
    return (
        <>
            <Filters onFiltersChange={handleFiltersChange} user={user} />


            {ads.length === 0 ? (
                <div>No ads found.</div>
            ) : (
                <>
                    <section className={styles.propertyGrid}>
                        {ads.map((ad) => (
                            <PropertyCard key={ad.id} link={ad.url} {...ad} />
                        ))}
                    </section>
                    <div style={{ padding: "1rem", marginBottom: "1rem" }}>
                    {renderPagination()}
                    </div>
                </>
            )}
        </>
    );
};

export default PropertyGrid;
