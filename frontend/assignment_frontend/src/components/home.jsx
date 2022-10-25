
import React, { Component } from 'react';
import { Route, Routes } from 'react-router-dom';
import Footer from '../components/footer';
import Header from '../components/header';
import LoginForm from '../components/loginForm';
import Navbar from '../components/navbar';
import ProductCard from '../components/productCard';
import ProductDetail from '../components/productDetail';
import RegisterForm from './registerForm';



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
                        <Route path='/register' element={<RegisterForm/>} />
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