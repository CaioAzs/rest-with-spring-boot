import React from 'react';
import { useNavigate } from "react-router-dom";
import './styles.css'
import { FiLinkedin, FiBook, FiUser, FiGithub } from 'react-icons/fi'

export default function MainPage() {

    const navigate = useNavigate();
    const hasToken = localStorage.getItem("accessToken") !== null;
    const hasUsername = localStorage.getItem('username') !== null;
    
    async function goToLogin() {
        navigate('/login')
    }

    async function goToBooks() {
        navigate('/book')
    }

    function openInNewTab(url) {
        window.open(url, "_blank", "noreferrer");
    }
 
    return (
        <div>
        <div className='mainPageRenderArea'>
            {/* // HEADER */}
            <div className='headerMainPage'>
                {/* LOGO */}
                <p className='logoText'>Books</p>
                {/* // MY BOOKS */}
                {hasToken && hasUsername && (
                    <div div className='myBooksButton' onClick={() => goToBooks()}>
                        <FiBook size={18} />

                        <p className="buttonsText">Books</p>
                    </div>
                )}

                {/* // LOGIN BTN */}
                <div className='loginIcon' onClick={() => goToLogin()}>
                    <FiUser size={28} color="000000" />
                </div>
        </div>
            
            {/* BODY */}
            <div className='bodyMainPage'>
                <div>
                    <p className='titleBody'>Books API</p>
                    <p className='subtitleBody'>A complete SpringBoot 3 + React CRUD</p>
                    <p className='subtitleBody'>Swagger, JWT Security, Content Negotiation (XML), Migrations (Flyway), Unit Testing (JUnit 5, Mockito, Mockaroo), File Download and Upload, Hibernate and JPA, HATEOAS, MySQL database.</p>
                </div>
                <img src="pile-of-books.png" alt='pile of book'/>
            </div>
            
        </div >
            {/* FOOTER */}
        <div className="footerMainPage">
                <p className='footerText'>
                Caio de Souza Conceição
                </p>   
                <div className='iconsFooter'>
                <div className='footerIcon' onClick={()=> openInNewTab("https://github.com/CaioAzs")}>
                    <FiGithub size={22}/>
                </div>
                <div className='footerIcon' onClick={()=> openInNewTab("https://www.linkedin.com/in/caioazs/")}>
                    <FiLinkedin size={22}/>
                </div>
                    </div>
            </div>
        </div>
    );
};