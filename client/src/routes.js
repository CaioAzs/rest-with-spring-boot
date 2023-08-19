import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import Login from './pages/Login';
import Book from './pages/Book';
import NewBook from './pages/NewBook';

export default function AppRoutes() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" exact element={<Login/>}/>
                <Route path="/book" element={<Book/>}/>
                <Route path="/book/new/:bookId" element={<NewBook/>}/>
            </Routes>
        </BrowserRouter>
    );
}