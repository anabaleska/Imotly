import React, { useState, useEffect } from 'react';
import { registerUser } from '../api/supabase';
import {Link, useNavigate} from 'react-router-dom';

const Register = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
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
        <div>
            <h2>Register</h2>
            <form onSubmit={handleRegister}>
                <input
                    type="text"
                    placeholder="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />
                <input
                    type="text"
                    placeholder="Surname"
                    value={surname}
                    onChange={(e) => setSurname(e.target.value)}
                    required
                />
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
                {error && <p style={{ color: 'red' }}>{error}</p>}  {/* Display error */}
                {success && <p style={{ color: 'green' }}>{success}</p>}  {/* Display success */}
                <button type="submit">Register</button>
            </form>
            <p>Already have an account? <Link to="/login">Login Here!</Link></p>
        </div>
    );
};

export default Register;
