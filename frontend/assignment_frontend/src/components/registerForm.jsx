import React, { Component } from 'react';
import '../CSS/login.css';
import * as accountService from './../service/accountService';
import Joi from 'joi-browser';
import From from './common/form';

class RegisterForm extends From {
    state = {
        data: {
            phoneNumber: "",
            fullName: "", email: "",
            password: ""
        },
        errors: {}
    };
    schema = {
        phoneNumber: Joi.string()
            .required()
            .label("Phone number"),
        fullName: Joi.string()
            .required()
            .label("Fullname"),
        email: Joi.string()
            .required()
            .label("Email"),
        password: Joi.string()
            .required()
            .min(5)
            .label("Password"),
        // avatar: Joi.string()
        //     .required()
        //     .label("Password"),

    };
    doSubmit = async () => {
        
        try {
            console.log("Submiting");
            await accountService.register(this.state.data);
            window.location = "/login";
            console.log("Succesfull");
        } catch (ex) {
            console.log("Errors");
            if (ex.response && ex.response.status === 400) {
                console.log("errors");
                const errors = { ...this.state.errors };
                errors.email = ex.response.data.mess;
                console.log("Mess" + errors);
                this.setState({ errors });
            }
        }

    };
    render() {

        return (
            <React.Fragment>
                <div class="row d-flex justify-content-center" style={ { display: 'block', backgroundColor: '#eee', height: '70em' } }>
                    <section class="h-100 gradient-form" style={ { backgroundColor: '#eee' } }>
                        <div class="container py-5 h-100">
                            <div class="row d-flex justify-content-center align-items-center h-100">
                                <div class="col-xl-10">

                                    <div class="card rounded-3 text-black">
                                        <form onSubmit={ this.handleSubmit } >
                                            <div class="row g-0">

                                                <div class="col-lg-6">
                                                    <div class="card-body p-md-5 mx-md-4">

                                                        <div class="text-center">
                                                            <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/lotus.webp"
                                                                style={ { width: '185px' } } alt="logo" />
                                                            <h4 class="mt-1 mb-5 pb-1">We are The Lotus Team</h4>
                                                        </div>


                                                        { this.renderInput('phoneNumber', 'Phonenumber') }
                                                        { this.renderInput("fullName", "Fullname") }
                                                        { this.renderInput('email', 'Email') }
                                                        { this.renderInput('password', 'Password', 'password') }

                                                        <div class="text-center pt-1 mb-5 pb-1">
                                                            { this.renderButton('Register') }
                                                            <div style={ { display: 'block' } }>
                                                                <input style={ { float: 'left' } } type="checkbox" name="remember-me" id="remember-me" />
                                                                <p style={ { float: 'left' } }>Remember me?</p>
                                                            </div>
                                                            <a class="text-muted" href="#!">Forgot password?</a>
                                                        </div>

                                                        <div class="d-flex align-items-center justify-content-center pb-4">
                                                            <p class="mb-0 me-2">Already Have Account?</p>
                                                            <a type="button" href="/login" class="btn btn-outline-danger">Login</a>
                                                        </div>



                                                    </div>
                                                </div>
                                                <div class="col-lg-6 d-flex align-items-center gradient-custom-2">
                                                    <div class="text-white px-3 py-4 p-md-5 mx-md-4">
                                                        <div class="col col-md-10">
                                                            <figure class="snip1336">
                                                                <figcaption>
                                                                    {/* <div>
                                                                        <img src="/images/banner1 (1).jpg" alt="profile-sample4"
                                                                            class="profile img-fluid" id="thumbnail" />
                                                                    </div><br />
                                                                    <div class="custom-file mt-3">
                                                                        <input value={this.state.data.avatar} name="image" type="file" class="custom-file-input"
                                                                            id="fileImage" accept=".png,.jpg" />
                                                                        <label class="custom-file-label" for="customFile">Choose
                                                                            file</label>
                                                                    </div> */}
                                                                </figcaption>
                                                            </figure>
                                                        </div>
                                                        <h4 class="mb-4">We are more than just a company</h4>
                                                        <p class="small mb-0">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
                                                            do eiusmod
                                                            tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                                                            quis nostrud
                                                            exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                                                    </div>
                                                </div>

                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
                {/* <p>Loginform</p> */ }
            </React.Fragment>
        );
    }
}

export default RegisterForm;