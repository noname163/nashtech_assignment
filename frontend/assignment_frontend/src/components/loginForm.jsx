import Joi from 'joi-browser';
import React from 'react';
import { ToastContainer, toast } from 'react-toastify';
import '../CSS/login.css';
import * as authenService from '../service/authenService';
import Form from './common/form';

class LoginForm extends Form {
    
    state={
        data:{email:"", password:""},
        errors:{}
    };
    schema ={
        email: Joi.string()
            .required()
            .label("Email"),
        password: Joi.string()
            .required()
            .min(5)
            .label("Password"),
    };
    doSubmit = async ()=>{
        try {
            console.log("submiting");
            await authenService.login(this.state.data);
            toast.success('Login Success.');
            window.location = "/";
        } catch (ex) {
            console.log("Errors"+ex);
            if (ex.response && ex.response.status === 500||ex.response && ex.response.status ==400)  {
                toast.error(ex.response.data.mess)
            }
        }
    };
    render() {
        return (
            <React.Fragment>
            <ToastContainer/>
               <div className="row d-flex justify-content-center" style={{display:'block', backgroundColor:'#eee', height:'50em'}}> 
               <section className="gradient-form" style={ { backgroundColor: '#eee', display:'block', width:'fit-content', height:'fit-content' } }>
                        <div className="container py-5 h-100" >
                            <div className="row d-flex justify-content-center align-items-center h-100">
                                <div className="col-xl-10" >
                                    <div className="card rounded-3 text-black">
                                        <div className="row g-0" >
                                            <div className="col-lg-6">
                                                <div className="card-body p-md-5 mx-md-4">

                                                    <div className="text-center">
                                                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/lotus.webp"
                                                            style={ { width: '185px' } } alt="logo" />
                                                        <h4 className="mt-1 mb-5 pb-1">We are The Lotus Team</h4>
                                                    </div>

                                                    <form onSubmit={this.handleSubmit} >
                                                        
                                                        {this.renderInput("email", "Email")}

                                                        {this.renderInput("password","Password","password")}

                                                        <div className="text-center pt-1 mb-5 pb-1">
                                                            {this.renderButton("Login")}
                                                            <div style={ { display: 'block' } }>
                                                                <input style={ { float: 'left' } } type="checkbox" name="remember-me" id="remember-me" />
                                                                <p style={ { float: 'left' } }>Remember me?</p>
                                                            </div>
                                                            <a className="text-muted" href="#!">Forgot password?</a>
                                                        </div>

                                                        <div className="d-flex align-items-center justify-content-center pb-4">
                                                            <p className="mb-0 me-2">Don't have an account?</p>
                                                            <button type="button" className="btn btn-outline-danger">Create new</button>
                                                        </div>

                                                    </form>

                                                </div>
                                            </div>
                                            <div className="col-lg-6 d-flex align-items-center gradient-custom-2">
                                                <div className="text-white px-3 py-4 p-md-5 mx-md-4">
                                                    <h4 className="mb-4">We are more than just a company</h4>
                                                    <p className="small mb-0">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
                                                        do eiusmod
                                                        tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                                                        quis nostrud
                                                        exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
               </div>
            </React.Fragment>
        );
    }
}

export default LoginForm;