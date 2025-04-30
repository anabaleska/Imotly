import './App.css';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { useState, useEffect } from 'react';


import Login from "./components/Login";
import Register from "./components/Register";
import Navbar from "./components/Navbar";
import InputDesign from "./components/InputDesign";
import PropertyGrid from "./components/PropertyGrid";
import Form from "./components/Form";
import Carousel from "./components/Carousel";
import {getUser, logoutUser} from "./api/supabase";
import {fetchAds} from "./api/adApi";

function App() {
    const [user, setUser] = useState(null);


    useEffect(() => {
        const fetchUser = async () => {
            const currentUser = await getUser();
            setUser(currentUser);
        };

        fetchUser();
    }, []);




    const handleLogout = async () => {
        try {
            await logoutUser();
            setUser(null);
        } catch (error) {
            console.error('Logout failed:', error);
        }
    };


    return (
        <Router>
            <div>
                <InputDesign />
                {/*<Navbar user={user} onLogout={handleLogout} /> /!* Navbar with logout button *!/*/}
                <Routes>
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/" element={

                            <>

                                <PropertyGrid/>
                                {/*<Rectangle />*/}
                                {/*<div style={{ display: 'flex', gap: '5px', padding: '30px' }}>*/}
                                {/*    <div style={{ flex: 1 }}>*/}
                                {/*        <Form />*/}
                                {/*    </div>*/}
                                {/*    <div style={{ flex: 1 }}>*/}
                                {/*        <Carousel />*/}
                                {/*    </div>*/}
                                {/*</div>*/}
                            </>


                    } />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
