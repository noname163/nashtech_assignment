import React, { Component } from "react";
import { Link } from "react-router-dom";
import Table from "./common/table";
import config from "../config.json";
import http from "../service/httpService";
import ProductModel from "./common/productModel";
import dataService from "../service/dataService";

class ProductTable extends Component {

    state = {
        posts: []
    };
    async componentDidMount() {
        const accessToken = localStorage.getItem("Access-Token");
        const options = {
            headers: {
                Authorizations: "Bearer "+accessToken
            }
        };
        const { data: posts } = await http.get("http://localhost:8080/admin/product", options);
        this.setState({ posts });
        
    }
    render() {
        console.log("Product: " + this.state.posts);
        return (
            <React.Fragment>
                <table className="table">
                    <thead className="thead-dark">
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Product Name</th>
                            <th scope="col">Categories</th>
                            <th scope="col">Price</th>
                            <th scope="col">Status</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        { this.state.posts.map((product,index) =>
                            <tr key={index}>
                                <th scope="row">{++index}</th>
                                <td>{ product?.name }</td>
                                <td>{ product?.categories }</td>
                                <td>{ product?.price }</td>
                                <td>{ product?.status }</td>
                                <td>
                                    <div className="btn-group" role="group" aria-label="Basic example">
                                        <Link to={"/edit-product"} state={product} className="btn btn-primary">Eidt</Link>
                                        <button type="button" className="btn btn-danger">Deleted</button>
                                    </div>
                                </td>
                            </tr>
                        ) }

                    </tbody>
                </table>
            </React.Fragment>
        );
    }
}

export default ProductTable;