import React, { useState, useEffect } from 'react';

const AdList = () => {
    const [ads, setAds] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchAds = async () => {
            try {
                const response = await fetch('http://localhost:5000/ads');
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                setAds(data);
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchAds();
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
                        <a href={ad.link} target="_blank" rel="noopener noreferrer"></a>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default AdList;
