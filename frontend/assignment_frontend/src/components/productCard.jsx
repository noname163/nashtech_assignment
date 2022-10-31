import React, { Component } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import Pagination from './pagination';
import { paginate, previousAndNextBtn } from '../JS/paginate';
import config from "../config.json";
import http from "../service/httpService";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "../CSS/productCard.css";

class ProductCard extends Component {

    state = {
        posts: [],
        pageSize: 6,
        currentPage: 1
    };
    async componentDidMount() {
        const { data: posts } = await http.get(config.apiEndpoint + "/products");
        this.setState({ posts });
    }
    handlePageChange = page => {
        this.setState({ currentPage: page })
    }
    handleBtn = st => {
        this.setState({ currentPage: previousAndNextBtn(this.state.posts.length, this.state.pageSize, this.state.currentPage, st) });

    }
    render() {
        const productData = paginate(this.state.posts, this.state.currentPage, this.state.pageSize);

        return (
            <React.Fragment>
                <ToastContainer />
                <div class="container mt-100" >
                    <div class="row">
                        { productData.map(item =>
                            <div class="col-md-4 col-sm-6" >
                                <div class="card mb-30" >
                                    <a class="card-img-tiles" href="#" data-abc="true"  >
                                        <div class="inner" >
                                            <div class="main-img" style={ { diplay: 'block', width: '30em', height: '20em' } }>
                                                <img src={ item.images.slice(0, 1).map(image => image.url) } alt="Category" style={ { diplay: 'block', width: 'max-width', height: '18em' } } />
                                            </div>
                                            <div class="thumblist" style={ { display: 'block', width: '8em', height: '20em' } } >
                                                { item.images.slice(1, 3).map(image => <img src={ image.url } alt="Category" style={ { display: 'block', width: '100%', height: '50%' } } />) }
                                            </div>
                                        </div>
                                    </a>
                                    <div class="card-body text-center">
                                        <h4 class="card-title">{ item.name }</h4>
                                        <p class="text-muted"><span>{ item.categories }</span> { item.price } VND</p>
                                        <Link class="btn btn-outline-primary btn-sm" to={ '/productDetail' } state={ item } data-abc="true" >
                                            View Products
                                        </Link>
                                    </div>
                                </div>
                            </div>

                        ) }
                    </div>
                </div>

                <Pagination itemsCount={ this.state.posts.length }
                    pageSize={ this.state.pageSize }
                    currentPage={ this.state.currentPage }
                    onPageChange={ this.handlePageChange }
                    btn={ this.handleBtn }
                />
            </React.Fragment>
        );
    }
}

export default ProductCard;