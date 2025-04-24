import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login from "./components/Login";
import AdList from "./components/AdList";
import Register from "./components/Register";
import Navbar from "./components/Navbar";
import InputDesign from "./components/InputDesign";
import PropertyGrid from "./components/PropertyGrid";
import Rectangle from "./components/Rectangle";
import Form from "./components/Form";

function App() {
    return (
        <Router>
            <div>
                <InputDesign />
                <Rectangle />
                    <Form />
                <Routes>
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/" element={<PropertyGrid />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
