import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = ({ user,role }) => {
    return (
        <React.Fragment>
            <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
                <a class="navbar-brand" href="#">Navbar</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>


                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                            <Link class="nav-link" to="/">Home <span class="sr-only">(current)</span></Link>
                        </li>
                        <li>
                        <Link class="nav-link" to="/productAdmin">Product <span class="sr-only">(current)</span></Link>
                        </li>
                        {
                            !user && (
                                <React.Fragment>
                                    <li class="nav-item active" >
                                        <Link class="nav-link" to="/login">Login <span class="sr-only">(current)</span></Link>
                                    </li>
                                    <li class="nav-item active" >
                                        <Link class="nav-link" to="/register">Register <span class="sr-only">(current)</span></Link>
                                    </li>
                                </React.Fragment>
                            )
                        }

                        {
                            role == 'ROLE_ADMIN' && (
                                <React.Fragment>
                                    <li class="nav-item dropdown" >
                                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                                            aria-haspopup="true" aria-expanded="false">
                                            Create
                                        </a>
                                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                            <Link to='/new_product'>
                                            <button class="dropdown-item" data-toggle="modal" data-target="#myModal" href="#">Product</button>
                                            </Link>
                                            <button class="dropdown-item" data-toggle="modal" data-target="#categoriesModal"
                                                href="#">Categories</button>
                                            <a class="dropdown-item" href="#">Price</a>
                                        </div>
                                    </li>
                                </React.Fragment>
                            )
                        }
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                                Filter
                            </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item" href="#">Categories</a>
                                <a class="dropdown-item" href="#">Name</a>
                                {/* <div class="dropdown-divider"></div>
              <a class="dropdown-item" href="#">Something else here</a>  */}
                            </div>
                        </li>
                        {
                            user && (
                                <React.Fragment>
                                    <li class="nav-item active">
                                        <a class="nav-link" href="/logout">Logout <span class="sr-only">(current)</span></a>
                                    </li>
                                </React.Fragment>
                            )
                        }
                    </ul>
                    <form class="form-inline my-2 my-lg-0">
                        <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" />
                        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
                    </form>
                </div>
            </nav>
        </React.Fragment>
    );
}

export default Navbar;