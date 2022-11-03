import React, { Component } from 'react';
import { Route, Routes } from 'react-router-dom';
import './App.css';
import NewProdut from './components/newProduct';
import Footer from './components/footer';
import Header from './components/header';
import LoginForm from './components/loginForm';
import Logout from './components/logout';
import Navbar from './components/navbar';
import ProductCard from './components/productCard';
import ProductDetail from './components/productDetail';
import ProductTable from './components/productTable';
import RegisterForm from './components/registerForm';
import auth from './service/authenService';
import { getProductData } from './service/dataService';
import EditProduct from './components/editProduct';

class App extends Component {
    state = {};
    componentDidMount() {
        try {
            const user = auth.getCurrentUser();
            const role = user.Roles;
            this.setState({ user });
            this.setState({ role });
        } catch (error) {

        }

    }
    render() {
        return (
            <React.Fragment>
                <Navbar user={ this.state.user } role={ this.state.role } />
                <Header />
                <hr />

                <Routes>
                    <Route path='/login' element={ <LoginForm /> } />
                    <Route path='/logout' element={ <Logout /> } />
                    <Route path='/product-detail' element={ <ProductDetail /> } />
                    <Route path='/product-admin' element={<ProductTable/>} />
                    <Route path='/edit-product' element={<EditProduct/>} />
                    <Route path='/register' element={ <RegisterForm /> } />
                    <Route path='/new-product' element={<NewProdut/>} />
                    <Route path='/' element={ <ProductCard /> } />
                </Routes>

                <hr />

                <Footer />
            </React.Fragment>
        );
    }
}

export default App;