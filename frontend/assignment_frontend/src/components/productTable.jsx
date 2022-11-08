import Joi from 'joi-browser';
import React, { Component } from "react";
import { Link } from "react-router-dom";
import { toast, ToastContainer } from 'react-toastify';
import swal from "sweetalert";
import http from "../service/httpService";
import productService from '../service/productService.js';


class ProductTable extends Component {

    state = {
        posts: [],
        category: {
            name: "",
            description: ""
        },
        errors: {},
        reload: false
    };
    schema = {
        name: Joi.string()
            .required()
            .label("Name"),
        description: Joi.string()
            .required()
            .label("Description"),

    };
    validate = () => {
        const options = { abortEarly: false };
        const { error } = Joi.validate(this.state.data, this.schema, options);
        if (!error) return null;

        const errors = {};
        for (let item of error.details) errors[item.path[0]] = item.message;
        return errors;
    };

    validateProperty = ({ name, value }) => {
        const obj = { [name]: value };
        const schema = { [name]: this.schema[name] };
        const { error } = Joi.validate(obj, schema);
        return error ? error.details[0].message : null;
    };
    async componentDidMount() {
        const accessToken = localStorage.getItem("Access-Token");
        const options = {
            headers: {
                Authorizations: "Bearer " + accessToken
            }
        };
        const { data: posts } = await http.get("http://localhost:8080/admin/product", options);
        this.setState({ posts });

    }
    async componentDidUpdate() {
        const accessToken = localStorage.getItem("Access-Token");
        const options = {
            headers: {
                Authorizations: "Bearer " + accessToken
            }
        };
        const { data: posts } = await http.get("http://localhost:8080/admin/product", options);
        this.setState({ posts });
    }
    onDelete = id => {
        let accessToken = localStorage.getItem("Access-Token");
        const options = {
            headers: {
                Authorizations: "Bearer " + accessToken
            }
        };
        return http.delete("http://localhost:8080/admin/product/" + id, options)
    }
    onConfirm = (id) => {
        swal({
            title: "Are you sure?",
            text: "Once deleted, you will not be able to recover this product again!",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((willDelete) => {
                if (willDelete) {
                    let accessToken = localStorage.getItem("Access-Token");
                    const options = {
                        headers: {
                            Authorizations: "Bearer " + accessToken
                        }
                    };
                    swal("Poof! Product has been deleted!", {
                        icon: "success",

                    });
                    http.delete("http://localhost:8080/admin/product/" + id, options)
                    this.setState({ reload: true })
                } else {
                    swal("Your imaginary file is safe!");
                }
            });
    }

    handleSubmit = e => {
        console.log("handleSubmi");
    
        e.preventDefault();
    
        const errors = this.validate();
        console.log(errors);
        this.setState({ errors: errors || {} });
        if (errors) return;
        this.newCategory();
      };

    newCategory = async () => {
        try {
            console.log("Submiting");
            await productService.newCategory(this.state.category);
            toast.success('Add New Category Success.');
            window.location = "/product-admin";

        } catch (ex) {
            console.log("Errors");
                console.log("errors");
                const errors = { ...this.state.errors };
                toast.error(ex.response.data.message)
                console.log("Message" + ex.response.data.message);
                this.setState({ errors });
        }
    }
    handleChange = ({ currentTarget: input }) => {
        const errors = { ...this.state.errors };
        const errorMessage = this.validateProperty(input);
        if (errorMessage) errors[input.name] = errorMessage;
        else delete errors[input.name];

        const category = { ...this.state.category };
        category[input.name] = input.value;
        console.log("Data: " + category.name);
        this.setState({ category, errors });

    }
    render() {
        return (
            <React.Fragment>
                <ToastContainer/>
                <Link to='/new-product' type="button" class="btn btn-outline-success btn-rounded mb-4" data-mdb-ripple-color="dark">New Product</Link>
                <button type="button" data-toggle="modal" data-target="#exampleModal" class="btn btn-outline-success btn-rounded ml-2 mb-4" data-mdb-ripple-color="dark">New Category</button>

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
                        { this.state.posts.map((product, index) =>
                            <tr key={ index }>
                                <th scope="row">{ ++index }</th>
                                <td>{ product?.name }</td>
                                <td>{ product?.categories }</td>
                                <td>{ product?.price }</td>
                                <td>{ product?.status }</td>
                                <td>
                                    <div className="btn-group" role="group" aria-label="Basic example">
                                        <Link to={ "/edit-product" } state={ product } className="btn btn-primary">Edit</Link>
                                        <button type="button" className="btn btn-danger" onClick={ () =>
                                            this.onConfirm(product?.id)
                                        }>Deleted</button>
                                    </div>
                                </td>
                            </tr>
                        ) }

                    </tbody>
                </table>


                <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">New Category</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form onSubmit={ this.handleSubmit }>
                                    <div class="form-group">
                                        <label for="category-name" class="col-form-label">Category Name:</label>
                                        <input type="text" name="name" class="form-control" id="category-name" onChange={ this.handleChange } />
                                    </div>
                                    <div class="form-group">
                                        <label for="description" class="col-form-label">Description:</label>
                                        <textarea class="form-control" name="description" id="description" onChange={ this.handleChange }></textarea>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <button type="submit" class="btn btn-primary">Save changes</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default ProductTable;