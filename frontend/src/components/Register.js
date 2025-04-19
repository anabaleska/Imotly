import React, { useState } from 'react';
import { registerUser } from '../api/supabase';  // Import the API call function for registering the user
import { useNavigate } from 'react-router-dom';  // Import useNavigate for redirection

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

        // Clear any previous error or success messages
        setError('');
        setSuccess('');

        try {
            const response = await registerUser(email, password, name, surname);

            // Check if the response contains an error message
            if (response.error) {
                // If there is an error, display it
                setError(response.error);
            } else {
                // If registration is successful
                setSuccess('Registration successful! Please log in.');
                localStorage.setItem('supabase_user', JSON.stringify(response.user));
                navigate('/login');
            }
        } catch (err) {
            // Handle unexpected errors (network, etc.)
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
