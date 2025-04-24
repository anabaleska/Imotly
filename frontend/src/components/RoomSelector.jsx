import React from "react";
import styles from "./Form.module.css";

const RoomSelector = () => {
    return (
        <button className={styles.div6}>
            <span className={styles.selectRooms}>Select Rooms</span>
            <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/56c031e5380e075dcb2ff43598f5073c542d9ca2?placeholderIfAbsent=true&apiKey=b5449d56610e47ad8c1812a0efbd1ee6" alt="Select arrow" className={styles.img3} />
        </button>
    );
};

export default RoomSelector;