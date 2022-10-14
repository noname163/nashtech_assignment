
import Header from '../components/header';
import Footer from '../components/footer';
import ProductCard from '../components/productCard';
import { BrowserRouter, Link, Route, Router, Routes } from 'react-router-dom'
import Navbar from '../components/navbar';
import React, { Component } from 'react';
import LoginForm from '../components/loginForm';
import ProductDetail from '../components/productDetail';
import axios from 'axios';
import Pagination from '../components/pagination';



class Home extends Component {

    render() {
        
        return (
            <React.Fragment>
                <Navbar />
                <Header />
                <hr />
                <div class="row d-flex justify-content-center" id="card-items">

                    <Routes>
                        <Route path='/login' element={ <LoginForm /> } />
                        <Route path='/productDetail' element={ <ProductDetail /> } />
                        <Route path='/' element={ <ProductCard/> } />
                    </Routes>
                </div>
                <hr />
                
                <Footer />
            </React.Fragment>
        );
    }
}

export default Home;