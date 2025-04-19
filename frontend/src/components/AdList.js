import React, { useState, useEffect } from 'react';
import {fetchAds} from "../api/adApi";

const AdList = () => {
    const [ads, setAds] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const getAds = async () => {
            try {
                const data = await fetchAds();
                setAds(data);
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        getAds();
    }, []);

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
        </div>
    );
};

export default AdList;
