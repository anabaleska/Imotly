import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from "./components/Login";
import AdList from "./components/AdList";
import Register from "./components/Register";

function App() {
    return (
        <Router>
            <div>
                <h1>Welcome to Pazar3 Ads</h1>

                {/* Define Routes for different pages */}
                <Routes>
                    {/* Route for the login page */}
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />

                    {/* Route for the main page, displaying the AdList */}
                    <Route path="/" element={<AdList />} />

                </Routes>
            </div>
        </Router>
    );
}

export default App;
