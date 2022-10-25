import React, { Component } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import Pagination from './pagination';
import { paginate, previousAndNextBtn } from '../JS/paginate';
import config from "../config.json";
import http from "../service/httpService";
import {ToastContainer} from "react-toastify";
import "react-toastify/dist/ReactToastify.css"

class ProductCard extends Component {

    state = {
        posts: [],
        pageSize: 6,
        currentPage: 1
    };
    async componentDidMount() {
        const { data: posts } = await http.get(config.apiEndpoint);
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
                <ToastContainer/>
                <section style={ { backgroundColor: '' } }>
                    <div >
                        <div class="row">
                            { productData.map(item =>
                                <div class="col-md-12 col-lg-4 mb-4 mb-lg-0 mt-4">
                                    <div id='card-items' class="card">
                                        <Link to="/productDetail">
                                            <div class="d-flex justify-content-between p-3">
                                                <p class="lead mb-0">Today's Combo Offer</p>
                                                <div
                                                    class="bg-info rounded-circle d-flex align-items-center justify-content-center shadow-1-strong"
                                                    style={ { width: '35px', height: '35px' } }>
                                                    <p class="text-white mb-0 small">x4</p>
                                                </div>
                                            </div>
                                            <img src="/images/test.jpg"
                                                class="card-img-top" alt="Laptop" />
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between">
                                                    <p class="small"><a href="#!" class="text-muted">1</a></p>
                                                    {/* <p class="small text-danger"><s>${item.price}</s></p> */ }
                                                </div>

                                                <div class="d-flex justify-content-between mb-3">
                                                    <h5 class="mb-0">{ item.name }</h5>
                                                    <h5 class="text-dark mb-0">${ item.price }</h5>
                                                </div>

                                                <div class="d-flex justify-content-between mb-2">
                                                    <p class="text-muted mb-0">Available: <span class="fw-bold">6</span></p>
                                                    <div class="ms-auto text-warning">
                                                        <i class="fa fa-star"></i>
                                                        <i class="fa fa-star"></i>
                                                        <i class="fa fa-star"></i>
                                                        <i class="fa fa-star"></i>
                                                        <i class="fa fa-star"></i>
                                                    </div>
                                                </div>
                                            </div>
                                        </Link>
                                    </div>
                                </div>

                            ) }


                        </div>
                    </div>
                </section>
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