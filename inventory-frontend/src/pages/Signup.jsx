import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { signup } from "../services/authService";

function Signup() {

    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        email: "",
        password: "",
        organizationName: ""
    });

    const handleChange = (e) => {

        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });

    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            await signup(formData);

            alert("Registration Successful");

            navigate("/");

        } catch (error) {

            alert(error.response?.data?.message || "Registration Failed");

        }

    };

    return (

        <div style={{ width: "350px", margin: "80px auto" }}>

            <h2>Signup</h2>

            <form onSubmit={handleSubmit}>

                <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={formData.email}
                    onChange={handleChange}
                    required
                />

                <br /><br />

                <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    value={formData.password}
                    onChange={handleChange}
                    required
                />

                <br /><br />

                <input
                    type="text"
                    name="organizationName"
                    placeholder="Organization Name"
                    value={formData.organizationName}
                    onChange={handleChange}
                    required
                />

                <br /><br />

                <button type="submit">
                    Register
                </button>

            </form>

            <br />

            <Link to="/">
                Already have an account?
            </Link>

        </div>

    );

}

export default Signup;