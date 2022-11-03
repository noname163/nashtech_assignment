import React, { Component } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import auth from '../service/authenService'
class Logout extends Component {
    componentDidMount(){
        auth.logout();
        toast.success('Logout Success.');
        window.location='/';
    }
    render() { 
        return (
            <ToastContainer/>
        );
    }
}
 
export default Logout;