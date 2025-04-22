import React, { useState } from 'react';
import { registerUser } from '../api/supabase';
import { useNavigate } from 'react-router-dom';

const Register = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

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
        </div>
    );
};

export default Register;
