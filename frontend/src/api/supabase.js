import { supabase } from './supabaseClient';


export const registerUser = async (email, password, name, surname) => {
    console.log("Form Data Being Sent:", email);

    try {
        const response = await fetch('http://localhost:5001/api/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password, name, surname }),
        });

        if (!response.ok) {
            const errorData = await response.json();
            return {
                error: errorData.msg || errorData.message || 'Registration failed',
            };
        }

        const result = await response.text();
        return { message: result };
    } catch (error) {
        return { error: error.message || 'An unexpected error occurred' };
    }
};

export const loginUser = async (email, password) => {
    try {
        const { data, error } = await supabase.auth.signInWithPassword({
            email,
            password
        });

        if (error) {
            return { error: error.message };
        }

        return { user: data.user };
    } catch (error) {
        return { error: error.message };
    }
};





// export const loginUser = async (email, password) => {
//     try {
//         const { data, error } = await supabase.auth.signInWithPassword({
//             email,
//             password,
//         });
//         console.log('Login response:', { data, error });
//
//         if (error) {
//             // Optional: Map specific Supabase error codes to friendlier messages
//             let message = error.message ;
//
//             if (error.message.toLowerCase().includes("invalid login credentials")) {
//                 message = "Incorrect email or password.";
//             }
//
//             return { error: message };
//         }
//
//         return { user: data.user, session: data.session };
//     } catch (err) {
//         console.error('Login exception:', err);
//         return { error: 'Unexpected error occurred. Please try again.' };
//     }
// };



export const getUser = async () => {
    const { data, error } = await supabase.auth.getUser();

    if (error) {
        console.error('Error fetching user:', error);
        return null;
    }

    return data?.user || null;
};


export const logoutUser = async () => {
    const { error } = await supabase.auth.signOut();
    if (error) throw error;
};

