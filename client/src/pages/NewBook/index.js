import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from "react-router-dom";
import './styles.css'
import api from '../../services/api'
import { FiArrowLeft } from 'react-icons/fi'


export default function NewBook() {

    const navigate = useNavigate();

    const [id, setId] = useState(null);
    const [author, setAuthor] = useState('');
    const [launchDate, setLaunchDate] = useState('');
    const [price, setPrice] = useState('');
    const [title, setTitle] = useState('');

    const { bookId } = useParams('');

    const accessToken = localStorage.getItem("accessToken");

    async function loadBook() {
        try {
            const response = await api.get(`api/book/v1/${bookId}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
            let adjDate = response.data.launchDate.split("T", 10)[0];

            setId(response.data.id)
            setTitle(response.data.title)
            setAuthor(response.data.author)
            setLaunchDate(adjDate)

        } catch (error) {
            alert(error);
            navigate('/book');
        }
    }

    useEffect(() => {
        if (bookId === '0') return;
        else loadBook();
    }, [bookId])

    async function saveOrUpdate(e) {
        e.preventDefault();

        const data = {
            title,
            author,
            launchDate,
            price,
        }

        try {
            if (bookId === '0') {
                await api.post('api/book/v1', data, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                })
                navigate('/book')
            } else {
                data.id = id;
                await api.put('api/book/v1', data, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                })
                navigate('/book')
            }

        } catch (error) {
            alert("Error recording book.")
        }
    }

    return (
        <div className="new-book-container">
            <div className="content">
                <div className="leftForm">
                    <div  className='backBtnHome' onClick={() => navigate('/book')}>
                        <FiArrowLeft size={16} color="251fc5" />
                        <p>Home</p>
                    </div><br/>
                    <h1>Add new book</h1>
                </div>
                <form onSubmit={saveOrUpdate}>
                    <input required placeholder='Title' type="text" value={title} onChange={e => setTitle(e.target.value)} />
                    <input required placeholder='Author' value={author} onChange={e => setAuthor(e.target.value)} />
                    <input required type='date' value={launchDate} onChange={e => setLaunchDate(e.target.value)} />
                    <input required placeholder='Price' value={price} onChange={e => setPrice(e.target.value)} />

                    <button className="defaultButton" type="submit">Add</button>
                </form>
            </div>

        </div>
    );
}