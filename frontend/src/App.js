import './App.css';
import {BrowserRouter as Router, Route, Routes, Navigate, useLocation, useNavigate} from 'react-router-dom';
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
import Modal from "./components/Modal";
import SavedSearches from "./components/SavedSearches";

function AppContent() {
    const [user, setUser] = useState(null);
    const [showLogin, setShowLogin] = useState(false);
    const location = useLocation();
    const navigate = useNavigate();

    const state = location.state;
    const background = state?.backgroundLocation;

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

            <div>
                <InputDesign />
                {/*<Navbar user={user} onLogout={handleLogout} /> /!* Navbar with logout button *!/*/}

                <Routes location={state?.backgroundLocation || location}>
                    {/*<Route path="/login" element={<Login />} />*/}
                    {/*<Route path="/register" element={<Register />} />*/}
                    <Route path="/" element={

                            <>
                                <PropertyGrid/>

                            </>


                    } />
                    <Route path="/saved-searches" element={<SavedSearches />} />


                </Routes>
                {state?.backgroundLocation && location.pathname === '/login' && (
                    <Modal onClose={() => navigate(-1)}>
                        <Login />
                    </Modal>
                )}
                {state?.backgroundLocation && location.pathname === '/register' && (
                    <Modal onClose={() => navigate(-1)}>
                        <Register />
                    </Modal>
                )}

            </div>

    );
}

function App() {
    return (
        <Router>
            <AppContent />
        </Router>
    );
}

export default App;
