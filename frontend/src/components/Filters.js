import React, { useState, useEffect } from "react";
import SearchInput from "./SearchInput";
import DropDown from "./DropDown";
import "./ToggleSwitch.css";
import RangeSlider from "./RangeSlider";
import styles from "./DropDown.module.css";

function Filters({ onFiltersChange, user }) {
    const [filters, setFilters] = useState({
        title: '',
        maxPrice: '',
        location: '',
        numRooms: '',
        floor: '',
        numFloors: '',
        maxSize: '',
        heating: '',
        typeOfObj: '',
        description: '',
        state: '',
        forSale: false,
        terrace: false,
        parking: false,
        furnished: false,
        basement: false,
        newBuilding: false,
        duplex: false,
        renovated: false,
        lift: false
    });
    const switchLabels = {
        forSale: "For Sale",
        terrace: "Terrace",
        parking: "Parking",
        furnished: "Furnished",
        basement: "Basement",
        newBuilding: "New Building",
        duplex: "Duplex",
        renovated: "Renovated",
        lift: "Lift"
    };

    const locations = ["", "Аеродром", "Арачиново", "Берово", "Битола", "Богданци",
        "Боговиње", "Босилово", "Брвеница",
        "Бутел", "Валандово", "Василево", "Вевчани", "Велес", "Виница",
        "Врапчиште", "Гази Баба", "Гевгелија", "Гостивар", "Градско", "Дебар",
        "Дебрца", "Делчево", "Демир Капија", "Демир Хисар", "Дојран", "Долнени",
        "Ѓорче Петров", "Желино", "Зелениково", "Зрновци", "Илинден", "Јегуновце", "Кавадарци",
        "Карбинци", "Карпош", "Кисела Вода", "Кичево", "Конче", "Кочани", "Кратово", "Крива Паланка", "Кривогаштани", "Крушево",
        "Куманово", "Липково", "Лозово", "Маврово и Ростушa", "Македонска Каменица",
        "Македонски Брод", "Могила", "Неготино", "Новаци", "Ново Село", "Охрид", "Петровец",
        "Пехчево", "Пласница", "Пласница", "Прилеп", "Пробиштип", "Радовиш", "Ранковце", "Ресен", "Росоман", "Скопје", "Сарај", "Свети Николе", "Сопиште",
        "Старо Нагоричане", "Струга", "Струмица", "Студеничани", "Теарце", "Тетово", "Центар", "Центар Жупа", "Чаир", "Чашка", "Чешиново Облешево",
        "Чучер Сандево", "Штип", "Шуто Оризари"]

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        const updatedFilters = {
            ...filters,
            [name]: type === 'checkbox' ? checked : value
        };
        setFilters(updatedFilters);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onFiltersChange(filters);
    };
    const [notifyMe, setNotifyMe] = useState(false);

    useEffect(() => {
        if (notifyMe) {
            handleNotifyMe();
        }
    }, [notifyMe]);
    const handleNotifyMe = async () => {
        try {
            console.log("Sending to backend:", {
                email: user.email,
                filters: filters,
            });
            const res = await fetch("http://localhost:5001/ads/subscribe", {

                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    email: user.email,
                    filters: filters,
                }),
            });

            const data = await res.json();
            alert(data.message || "Subscription successful.");
        } catch (err) {
            console.error("Subscription failed", err);
            alert("Could not subscribe for notifications.");
        }
    };

    console.log(user);
    return (
        <form onSubmit={handleSubmit} style={{ padding: '10px 220px', display: 'grid', gap: '10px',}}>
            <h1 style={{textAlign:'center', fontWeight:'lighter',color:"#3a0ca3"}}>Filter Ads</h1>
            <SearchInput name="title" placeholder="Наслов" value={filters.title} onChange={handleChange} />
            <div style={{
                display: 'flex',
                flexWrap: 'wrap',

                justifyContent: 'space-between',
                }}>
            <DropDown name="location" label="Локација" value={filters.location} onChange={handleChange} options={locations} />
            <DropDown name="typeOfObj" label="Тип на објект" value={filters.typeOfObj} onChange={handleChange} options={["", "Стан", "Куќа/Вила"]} />
            <DropDown name="heating" label="Греење" value={filters.heating} onChange={handleChange} options={["", "Централно", "Струја", "Дрва", "Нема", "Соларна енергија", "Друго"]} />
            <DropDown name="state" label="Состојба" value={filters.state} onChange={handleChange} options={["", "Нов", "Стар","Користен","Реновиран","Во градба"]} />

            </div>
            <div style={{
                display: 'flex',
                flexWrap: 'wrap',
                gap: '68px',

            }}>
            {/*<input type="number" name="maxPrice" placeholder="Max Price" value={filters.maxPrice} onChange={handleChange} />*/}
            <input  className={styles.inputLikeDropdown} type="number" name="numRooms" placeholder="Number of Rooms" value={filters.numRooms} onChange={handleChange} />
            <input className={styles.inputLikeDropdown} type="number" name="floor" placeholder="Floor" value={filters.floor} onChange={handleChange} />
            <input className={styles.inputLikeDropdown} type="number" name="numFloors" placeholder="Number of Floors" value={filters.numFloors} onChange={handleChange} />
            </div>

            <div style={{padding:'10px', display: 'flex', flexWrap: 'wrap', justifyContent: 'space-between' ,backgroundColor:"#6a6dcd",
            borderRadius:'20px'}}>
                {Object.entries(switchLabels).map(([key, label]) => (
                    <div key={key} className="switch-container">
                        <label className="switch">
                            <input type="checkbox" name={key} checked={filters[key]} onChange={handleChange} />
                            <span className="slider" />
                        </label>
                        <span style={{color:"white"}}>{label}</span>
                    </div>
                ))}
            </div>

            <div style={{

                flexWrap: 'wrap',
                gap: '10px',
                justifyContent: 'space-between',

            }}>
                <RangeSlider
                name="maxPrice"
                label="Макс. Цена"
                label2="еур"
                value={filters.maxPrice}
                min={0}
                max={250000}
                onChange={handleChange}
            />


            <RangeSlider
                name="maxSize"
                label="Големина"
                label2="m²"
                value={filters.maxSize}
                min={10}
                max={500}
                onChange={handleChange}
            />
            </div>
            {/*<input type="number" name="maxSize" placeholder="Size (m²)" value={filters.maxSize} onChange={handleChange} />*/}
            {/*<input type="text" name="state" placeholder="State" value={filters.state} onChange={handleChange} />*/}


            {user ? (
            <div style={{ textAlign:'center' }}>
                {notifyMe ? (
                    <button disabled style={{ width:'300px', backgroundColor: "#2a2a55", color: "white", padding: "0.75rem 1.5rem", border: "none", borderRadius: "8px", cursor: "not-allowed" }}>
                        ✅ You're in! We'll notify you.
                    </button>
                ) : (
                    <button onClick={() => setNotifyMe(true)} style={{  width:'300px', backgroundColor: "#6a6dcd", color: "white", padding: "0.75rem 1.5rem", border: "none", borderRadius: "8px", cursor: "pointer" }}>
                        🔔 Notify me about similar properties
                    </button>
                )}
            </div>): (<div style={{
                padding: "1rem",
                textAlign: "center",
                backgroundColor: "#f8d7da",
                color: "#721c24",
                borderRadius: "8px",
                margin: "1rem"
            }}>
                <strong>🔔 Login to be notified.</strong>
            </div>)}

            <div style={{ textAlign:'center' }}>
            <button style={{ width:'300px', backgroundColor: "#2a2a55", color: "white", padding: "0.75rem 1.5rem", border: "none", borderRadius: "8px", cursor: "pointer" }} type="submit">Apply Filters</button>
            </div>
            </form>
    );
}

export default Filters;
