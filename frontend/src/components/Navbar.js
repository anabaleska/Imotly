import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Navbar = () => {
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem('supabase_user'));

    const handleLogout = () => {
        localStorage.removeItem('supabase_user');
        navigate('/login');
    };

    return (
        <nav>
            <Link to="/">Home</Link>

            {user ? (
                <>
                    <span style={{ marginLeft: '1rem' }}>Welcome, {user.email}</span>
                    <button onClick={handleLogout} style={{ marginLeft: '1rem' }}>Logout</button>
                </>
            ) : (
                <>
                    <Link to="/login" style={{ marginLeft: '1rem' }}>Login</Link>
                    <Link to="/register" style={{ marginLeft: '1rem' }}>Register</Link>
                </>
            )}
        </nav>
    );
};

export default Navbar;
