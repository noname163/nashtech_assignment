import React from 'react';
import { Link } from 'react-router-dom';
import { swal } from 'sweetalert';
const Navbar = ({ user, role }) => {
    return (
        <React.Fragment>
            <nav className="navbar navbar-expand-lg navbar-light bg-light fixed-top">
                <Link to="/" className="navbar-brand">Home</Link>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>


                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav mr-auto">
                        { role == 'ROLE_USER' && (
                            <React.Fragment>
                                <li className="nav-item active">
                                    <Link className="nav-link" to="/cart">Cart <span className="sr-only">(current)</span></Link>
                                </li>
                                <li>
                                    <Link className="nav-link" to="/view-order">Order <span className="sr-only">(current)</span></Link>
                                </li>
                            </React.Fragment>
                        ) }
                        {
                            !user && (
                                <React.Fragment>
                                    <li className="nav-item active" >
                                        <Link className="nav-link" to="/login">Login <span className="sr-only">(current)</span></Link>
                                    </li>
                                    <li className="nav-item active" >
                                        <Link className="nav-link" to="/register">Register <span className="sr-only">(current)</span></Link>
                                    </li>
                                </React.Fragment>
                            )
                        }

                        {
                            role == 'ROLE_ADMIN' && (
                                <React.Fragment>
                                    <li className="nav-item dropdown" >
                                        <a className="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                                            aria-haspopup="true" aria-expanded="false">
                                            Manage
                                        </a>
                                        <div className="dropdown-menu" aria-labelledby="navbarDropdown">

                                            <Link to="/product-admin">
                                                <button className="dropdown-item" data-toggle="modal" data-target="#categoriesModal"
                                                    href="#" >Manage Product</button> <span className="sr-only">(current)</span>
                                            </Link>
                                            <Link to="/account-admin">
                                                <button className="dropdown-item" data-toggle="modal" data-target="#categoriesModal"
                                                    href="#" >Manage Account</button> <span className="sr-only">(current)</span>
                                            </Link>
                                            <Link to='/manage-order'>
                                                <button className="dropdown-item" data-toggle="modal" data-target="#myModal" href="#">Manage Order</button>
                                            </Link>
                                        </div>
                                    </li>
                                </React.Fragment>
                            )
                        }
                        <li className="nav-item dropdown">
                            <a className="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                                Filter
                            </a>
                            <div className="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a className="dropdown-item" href="#">Categories</a>
                                <a className="dropdown-item" href="#">Name</a>
                                {/* <div className="dropdown-divider"></div>
              <a className="dropdown-item" href="#">Something else here</a>  */}
                            </div>
                        </li>
                        {
                            user && (
                                <React.Fragment>
                                    <li className="nav-item active">
                                        <a className="nav-link" href="/logout">Logout <span className="sr-only">(current)</span></a>
                                    </li>
                                </React.Fragment>
                            )
                        }
                    </ul>
                    <form className="form-inline my-2 my-lg-0">
                        <input className="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" />
                        <button className="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
                    </form>
                </div>
            </nav>
        </React.Fragment>
    );
}

export default Navbar;