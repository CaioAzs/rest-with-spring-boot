import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from "react-router-dom";
import './styles.css'
import api from '../../services/api'
import { FiPower, FiEdit, FiTrash2 } from 'react-icons/fi'
import logoImage from '../../assets/bob.jpg'

export default function Book() {

    const [books, setBooks] = useState([]);
    const [page, setPage] = useState(0);

    const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem("accessToken");

    const navigate = useNavigate();

    async function editBook(id) {
        try {
            navigate(`/book/new/${id}`)
        } catch (error) {
            alert(error);
        }
    }

    async function fetchMoreBooks(){
        const response = await api.get("api/book/v1", {
            headers: {
                Authorization: `Bearer ${accessToken}`
            },
            params: {
                page: 0,
                limit: 2,
                direction: "asc",
            }
        });

        setBooks([...books, ...response.data._embedded.bookVOList])
        setPage(page+1);
    }

    async function logout(id) {
        localStorage.clear();
        navigate('/');
    }
    async function deleteBook(id) {
        try {
            await api.delete(`api/book/v1/${id}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
            setBooks(books.filter(book => book.id !== id))
        
        } catch (error) {
        alert('Delete failed.')
    }
}

useEffect(() => {
    fetchMoreBooks();
}, []);


return (
    <div className="book-container">
        <header>
            <img src="logoImage" alt="logoImage" />
            <span>Welcome, <strong>{username.toUpperCase()}</strong>!</span>
            <Link className="button" to="/book/new/0">Add new Book</Link>
            <button type='button' onClick={logout}>
                <FiPower size={18} color="251FC5" />
            </button>
        </header>

        <h1>Registered Books</h1>
        <ul>
            {books.map(book => (
                <li key={book.id}>
                    <strong>Title:</strong>
                    <p>{book.title}</p>
                    <strong>Author:</strong>
                    <p>{book.author}</p>
                    <strong>Price:</strong>
                    <p>{Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(book.price)} </p>
                    <strong>Release Date:</strong>
                    <p>{Intl.DateTimeFormat('pt-BR').format(new Date(book.launchDate))}</p>

                    <button type='button' onClick={() => editBook(book.id)}>
                        <FiEdit size={20} color="251FC5" />
                    </button>
                    <button onClick={()=> deleteBook(book.id)}type='button'>
                        <FiTrash2 size={20} color="251FC5" />
                    </button>
                </li>
            ))}

        </ul>
                <button className="button" onClick={fetchMoreBooks} type="button">Load more books...</button>
    </div>
);
}