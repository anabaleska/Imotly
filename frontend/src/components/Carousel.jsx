import React, { useState, useEffect } from "react";
import { fetchAds } from "../api/adApi";
import styles from "./Carousel.module.css";


const Carousel = () => {
    const [images, setImages] = useState([]);
    const [currentIndex, setCurrentIndex] = useState(0);

    useEffect(() => {
        const getLastFourAds = async () => {
            try {
                const data = await fetchAds(0, 10);
                const adsWithImages = data.filter(ad => ad.imageurl);
                const lastFour = adsWithImages.slice(-4);
                const urls = lastFour.map(ad => ad.imageurl);
                setImages(urls);
            } catch (error) {
                console.error("Failed to fetch ads:", error);
            }
        };

        getLastFourAds();
    }, []);

    const prevSlide = () => {
        setCurrentIndex((prev) => (prev === 0 ? images.length - 1 : prev - 1));
    };

    const nextSlide = () => {
        setCurrentIndex((prev) => (prev === images.length - 1 ? 0 : prev + 1));
    };

    if (images.length === 0) {
        return <div className="text-center py-10">Loading carousel...</div>;
    }

    return (
        <div className="relative w-full max-w-2xl mx-auto overflow-hidden rounded-2xl shadow-lg">
            <img
                src={images[currentIndex]}
                alt={`Ad ${currentIndex + 1}`}
                className="w-full h-96 object-cover transition-all duration-300"
            />
            <button
                onClick={prevSlide}
                className="absolute top-1/2 left-4 transform -translate-y-1/2 bg-white rounded-full p-2 shadow hover:bg-gray-100"
            >
                ◀
            </button>
            <button
                onClick={nextSlide}
                className="absolute top-1/2 right-4 transform -translate-y-1/2 bg-white rounded-full p-2 shadow hover:bg-gray-100"
            >
                ▶
            </button>
            <div className="flex justify-center mt-4 space-x-2 absolute bottom-4 left-1/2 transform -translate-x-1/2">
                {images.map((_, index) => (
                    <span
                        key={index}
                        className={`h-2 w-2 rounded-full ${
                            index === currentIndex ? "bg-blue-600" : "bg-gray-300"
                        }`}
                    />
                ))}
            </div>
        </div>
    );
};

export default Carousel;
