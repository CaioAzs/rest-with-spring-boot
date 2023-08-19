import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './styles.css';
import api from '../../services/api'
export default function Login() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();

    async function login(e) {
        e.preventDefault();
        const data = {
            username,
            password,
        };
        try {
            const response = await api.post('auth/signin', data);
            localStorage.setItem("username", username);
            localStorage.setItem("accessToken", response.data.accessToken);

            navigate('/book');
        } catch (error) {
            console.log(error);
            alert('Login Failed! Try again!');
        }
    }

    return (
        <div>
            <section>
                <div class="form-box">
                    <div class="form-value">
                        <form onSubmit={login}>
                            <h2>Login</h2>
                            <div class="inputbox">
                                <input required value={username} onChange={e => setUsername(e.target.value)} />
                                <label for="">Username</label>
                            </div>
                            <div class="inputbox">
                                <input type="password" required value={password} onChange={e => setPassword(e.target.value)} />
                                <label for="">Password</label>
                            </div>
                            <button className="button" type="submit">Log in</button>
                        </form>
                    </div>
                </div>
            </section>
        </div>
    );
}