import Joi from 'joi-browser';
import React, { Component } from "react";
import { Link } from "react-router-dom";
import { toast, ToastContainer } from 'react-toastify';
import swal from "sweetalert";
import http from "../service/httpService";
import productService from '../service/productService.js';


class AccountTable extends Component {

    state = {
        posts: [],
        errors: {},
        reload: false
    };
    async componentDidMount() {

        const accessToken = localStorage.getItem("Access-Token");
        const options = {
            headers: {
                Authorizations: "Bearer " + accessToken
            }
        };
        const { data: posts } = await http.get("http://localhost:8080/admin/accounts", options);
        this.setState({ posts });
        console.log("data: " + this.state.posts)
    }
    // async componentDidUpdate() {
    //     const accessToken = localStorage.getItem("Access-Token");
    //     const options = {
    //         headers: {
    //             Authorizations: "Bearer " + accessToken
    //         }
    //     };
    //     const { data: posts } = await http.get("http://localhost:8080/admin/accounts", options);
    //     this.setState({ posts });

    // }
    onConfirm = (id) => {
        swal({
            title: "Are you sure?",
            text: "Once deactive, this account will not be able to login!",
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
                    http.delete("http://localhost:8080/admin/accounts/" + id, options)
                    window.location.reload();
                } else {
                    swal("Your cancel deactive action!");
                }
            });
    }
    onActive = (id) => {
        swal({
            title: "Are you sure?",
            text: "Once Active, this account will be able to login!",
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
                    swal("Great! Account has been active!", {
                        icon: "success",

                    });
                    try {
                        http.patch("http://localhost:8080/admin/accounts/active/" + id, id, options)
                        window.location.reload();
                    } catch (error) {
                        console.log("error " + error.response.data.message)
                    }
                } else {
                    swal("Your cancel active action!");
                }
            });
    }


    handlePromote = (id) => {
        swal({
            title: "Are you sure?",
            text: "Once promote to admin, this account will be able to access sensitive information!",
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
                    swal("Great! Account has been promote!", {
                        icon: "success",

                    });
                    try {
                        http.patch("http://localhost:8080/admin/accounts/update-role/" + id, id, options)
                        window.location.reload();
                    } catch (error) {
                        console.log("error " + error.response.data.message)
                    }
                } else {
                    swal("Your cancel promote action!");
                }
            });
    }
    render() {
        return (
            <React.Fragment>
                <ToastContainer />
                <Link to='/new-product' type="button" class="btn btn-outline-success btn-rounded mb-4" data-mdb-ripple-color="dark">New Product</Link>
                <button type="button" data-toggle="modal" data-target="#exampleModal" class="btn btn-outline-success btn-rounded ml-2 mb-4" data-mdb-ripple-color="dark">New Category</button>

                <table className="table">
                    <thead className="thead-dark">
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Email</th>
                            <th scope="col">Phone number</th>
                            <th scope="col">Fullname</th>
                            <th scope="col">Status</th>
                            <th scope="col">Role</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        { this.state.posts.map((account, index) =>
                            <tr key={ index }>
                                <th scope="row">{ ++index }</th>
                                <td>{ account?.email }</td>
                                <td>{ account?.phoneNumber }</td>
                                <td>{ account?.fullName }</td>
                                <td>{ account?.role?.role }</td>
                                <td>{ account?.status }</td>
                                <td>
                                    <div className="btn-group" role="group" aria-label="Basic example">
                                        { account?.role?.role == 'ROLE_ADMIN' || account?.status == 'DEACTIVE' ? "" : <button onClick={ () => this.handlePromote(account?.id) } className="btn btn-primary">Promote</button> }
                                        { account?.role?.role == 'ROLE_ADMIN' || account?.status == 'DEACTIVE' ? "" : <button type="button" className="btn btn-danger" onClick={ () =>
                                            this.onConfirm(account?.id)
                                        }>Deleted</button> }
                                        { account?.status == 'DEACTIVE' ? <button type="button" className="btn btn-primary" onClick={ () =>
                                            this.onActive(account?.id)
                                        }>Active</button> : "" }
                                    </div>
                                </td>
                            </tr>
                        ) }

                    </tbody>
                </table>


                {/* <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
                </div> */}
            </React.Fragment>
        );
    }
}

export default AccountTable;