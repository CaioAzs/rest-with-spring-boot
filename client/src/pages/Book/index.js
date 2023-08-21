import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from "react-router-dom";
import './styles.css'
import api from '../../services/api'
import { FiEdit, FiTrash2, FiLogIn } from 'react-icons/fi'

export default function Book() {

    const [books, setBooks] = useState([]);
    const [page, setPage] = useState(0);
    const [searchTerm, setSearchTerm] = useState('');
    const [searchResults, setSearchResults] = useState([]);
    const [searchActive, setSearchActive] = useState(false);

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

    async function searchBooks() {
        try {
            const response = await api.get(`api/book/v1/findbookbyname/${searchTerm}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            });
            console.log(response)
            setSearchResults(response.data.content);

        } catch (error) {
            console.error("Error searching for books:", error);
        }
    }

    async function fetchMoreBooks() {
        try {
            const response = await api.get("api/book/v1", {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                },
                params: {
                    page: page,
                    size: 4,
                    direction: "asc",
                }
            });

            // Check if there are no more books in the response
            if (response.data._embedded && response.data._embedded.bookVOList.length === 0) {
                alert("No more books to load.");
                // Optionally, you can disable the "Load More Books..." button here
            } else {
                setBooks([...books, ...response.data._embedded.bookVOList]);
                setPage(page + 1);
            }
        } catch (error) {
            console.error("Error fetching more books:", error);
        }
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
                <img src="book-stack.png" alt="logoImage" onClick={() => navigate('/')} className="image-header" />
                <span>Welcome, <strong>{username.charAt(0).toUpperCase() + username.slice(1)}</strong>!</span>
                <Link className="defaultButton" to="/book/new/0">Add new book</Link>

                <div className={"defaultButton1"} onClick={logout}>
                    <FiLogIn size={20} />
                </div>
            </header>

            <div className='searchGeneral'>
                <input className='searchBar'
                    type="text"
                    placeholder="Search by title..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
                <div className="searchButton" onClick={() => {
                    setSearchActive(true); // Ativa a busca
                    searchBooks(); // Realiza a busca
                }}>Search
                </div>
            </div>
            <h1>Registered Books</h1>
            {searchActive ? (
                <div className="search-results">
                    <ul>
                        {searchResults.map(book => (
                            <li key={book.id}>
                                <strong>Title:</strong>
                                <p>{book.title}</p>
                                <strong>Author:</strong>
                                <p>{book.author}</p>
                                <strong>Price:</strong>

                                <p>{Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(book.price)} </p>
                                <strong>Release Date:</strong>
                                <p>{book.launchDate ? Intl.DateTimeFormat('pt-BR').format(new Date(book.launchDate)) : ''}</p>

                                <div onClick={() => editBook(book.id)} className="defaultButton">
                                    <FiEdit size={20} />
                                </div>
                                <div onClick={() => deleteBook(book.id)} className="defaultButton">
                                    <FiTrash2 size={20} />
                                </div>
                            </li>
                        ))}
                    </ul>
                    <div className="fetchButton" onClick={() => setSearchActive(false)}>Show all books again</div>
                </div>
            ) : (
                <div className="book-list">
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


                                <div onClick={() => editBook(book.id)} className="defaultButton">
                                    <FiEdit size={20} />
                                </div>
                                <div onClick={() => deleteBook(book.id)} className="defaultButton">
                                    <FiTrash2 size={20} />
                                </div>
                            </li>
                        ))}
                    </ul>
                    <div className="fetchButton" onClick={fetchMoreBooks}>Load More Books...</div>
                </div>
            )}

        </div>
    );
}