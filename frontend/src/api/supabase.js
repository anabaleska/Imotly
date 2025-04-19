import { supabase } from './supabaseClient';


export const registerUser = async (email, password, name, surname) => {
    try {
        const response = await fetch('http://localhost:5001/api/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email,
                password,
                name,
                surname
            }),
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Registration failed');
        }

        const result = await response.text(); // or use .json() if your backend returns JSON
        return { message: result };
    } catch (error) {
        return { error: error.message };
    }
};





export const loginUser = async (email, password) => {
    const { user, error } = await supabase.auth.signInWithPassword({
        email,
        password
    });
    return { user, error };
};

export const getUser = async () => {
    const user = supabase.auth.user();
    return user;
};

export const logoutUser = async () => {
    await supabase.auth.signOut();
};
