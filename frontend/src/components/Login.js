import React, { useState, useEffect } from 'react';
import { loginUser } from '../api/supabase';
import {Link, useNavigate} from 'react-router-dom';
import Register from "./Register";

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);



    useEffect(() => {
        const user = localStorage.getItem('supabase_user');
        if (user) {
            try {
                const parsedUser = JSON.parse(user);
                if (parsedUser) {
                    navigate('/');
                } else {
                    setLoading(false); //
                }
            } catch (e) {
                console.error('Error parsing user data:', e);
                setLoading(false);
            }
        } else {
            setLoading(false);
        }
    }, [navigate]);

    const handleLogin = async (e) => {
        e.preventDefault();

        const { user, error } = await loginUser(email, password);

        if (error) {
            setError(error.message);
        } else {
            console.log('User logged in:', user);

            localStorage.setItem('supabase_user', JSON.stringify(user));
            navigate('/');
        }
    };
    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                {error && <p style={{color:"red"}}>{error}</p>}
                <button type="submit">Login</button>
            </form>
            <p>Don't have an account? <Link to="/register">Register Here!</Link></p>
        </div>
    );
};
export default Login;
