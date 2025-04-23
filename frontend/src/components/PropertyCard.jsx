import React from "react";
import styles from "./PropertyCard.module.css";


const PropertyCard = ({
                          imageurl,
                          price,
                          title,
                          location,
                          link
                      }) => {
    return (
        <a href={link} target="_blank" rel="noopener noreferrer" className={styles.cardLink} style={{ textDecoration: "none" }}>
        <article className={styles.propertyCard}>
            {imageurl && (
                <img src={imageurl} alt={title} className={styles.cardImage} />
            )}
            <div className={styles.cardContent}>
                <h2 className={styles.price}>${price}</h2>
                <h3 className={styles.title}>{title}</h3>
                <p className={styles.location}>{location}</p>
            </div>
        </article></a>
    );
};

export default PropertyCard;
