import React, { useState, useEffect } from 'react';
import {fetchAds} from "../api/adApi";

const AdList = () => {
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
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        getAds();
    }, [page]);

    const handlePrev = () => {
        if (page > 0) setPage(page - 1);
    };

    const handleNext = () => {
        setPage(page + 1);
    };

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;

    return (
        <div>
            <h2>Ads List</h2>
            <ul>
                {ads.map((ad) => (
                    <li key={ad.link}>
                        <h3>{ad.title}</h3>
                        <p>Price: {ad.price}</p>
                        <p>Location: {ad.location}</p>
                        <p>Date Posted: {ad.date_posted}</p>
                        {ad.imageurl && <img src={ad.imageurl} alt={ad.title} />}
                        <a href={ad.link} target="_blank" rel="noopener noreferrer">View Ad</a>
                    </li>
                ))}
            </ul>
            <div style={{ marginTop: "20px" }}>
                <button onClick={handlePrev} disabled={page === 0}>Previous</button>
                <span style={{ margin: "0 10px" }}>Page {page + 1}</span>
                <button onClick={handleNext}>Next</button>
            </div>
        </div>
    );
};

export default AdList;
