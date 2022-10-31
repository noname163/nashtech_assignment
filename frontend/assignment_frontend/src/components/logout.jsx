import React, { Component } from 'react';
import auth from '../service/authenService'
class Logout extends Component {
    componentDidMount(){
        auth.logout();
        window.location='/';
    }
    render() { 
        return (null);
    }
}
 
export default Logout;