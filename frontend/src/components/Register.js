import React, { useState, useEffect } from 'react';
import { registerUser } from '../api/supabase';
import {Link, useLocation, useNavigate} from 'react-router-dom';
import styles from './Register.module.css';

const Register = ({ onClose }) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
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

    const handleRegister = async (e) => {
        e.preventDefault();
        setError('');
        setSuccess('');

        try {
            const response = await registerUser(email, password, name, surname);

            if (response.error) {
                setError(response.error);
            } else {
                setSuccess('Registration successful! Please log in.');
                localStorage.setItem('supabase_user', JSON.stringify(response.user));
                if (onClose) onClose(); // close modal if opened in modal
                navigate('/login');
            }
        } catch (err) {
            setError('An unexpected error occurred. Please try again.');
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className={styles.card}>
            <h2 className={styles.title}>Register</h2>
            <form onSubmit={handleRegister}>
                <input
                    type="text"
                    className={styles.input}
                    placeholder="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />
                <input
                    type="text"
                    className={styles.input}
                    placeholder="Surname"
                    value={surname}
                    onChange={(e) => setSurname(e.target.value)}
                    required
                />
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
                {success && <p className={styles.success}>{success}</p>}
                <button type="submit" className={styles.button}>Register</button>
            </form>
            <div className={styles.link}>
                Already have an account? <Link to="/login" state={{ backgroundLocation: location }}>Login Here!</Link>
            </div>
        </div>
    );
};

export default Register;
