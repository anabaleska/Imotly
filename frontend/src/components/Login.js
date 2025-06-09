import React, { useState, useEffect } from 'react';
import { loginUser } from '../api/supabase';
import {Link, useLocation, useNavigate} from 'react-router-dom';
import styles from './Login.module.css';

const Login = ({ onClose }) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);
    const location = useLocation()

    useEffect(() => {
        const user = localStorage.getItem('supabase_user');
        if (user) {
            try {
                const parsedUser = JSON.parse(user);
                if (parsedUser) {
                    navigate('/');
                } else {
                    setLoading(false);
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
            localStorage.setItem('supabase_user', JSON.stringify(user));
            if (onClose) onClose();
            navigate('/');
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className={styles.card}>
            <h2 className={styles.title}>Login</h2>
            <form onSubmit={handleLogin}>
                <input
                    type="email"
                    className={styles.input}
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <input
                    type="password"
                    className={styles.input}
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                {error && <p className={styles.error}>{error}</p>}
                <button type="submit" className={styles.button}>Login</button>
            </form>
            <div className={styles.link}>
                Don’t have an account? <Link to="/register" state={{ backgroundLocation: location.state?.backgroundLocation || location }}>
                Register Here!
            </Link>
            </div>
        </div>
    );
};

export default Login;
