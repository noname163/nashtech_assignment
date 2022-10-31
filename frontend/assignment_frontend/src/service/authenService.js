
import config from "../config.json"
import http from "../service/httpService"
import jwtDecode from 'jwt-decode';

const apiEndpoint = config.apiEndpoint;
console.log("Api: " + apiEndpoint);
const accessToken = "Access-Token";
const refreshToken = "Refresh-Token";
export async function login(user){
    const {data:jwt} = await http.post(apiEndpoint + "/login",{
        email: user.email,
        password: user.password
    });
    localStorage.setItem(accessToken,jwt.accessToken);
    localStorage.setItem(refreshToken,jwt.refeshToken);
}

export function logout(){
    localStorage.removeItem(accessToken);
}

export function getCurrentUser(){
    try {
        const token = localStorage.getItem(accessToken);
        const user = jwtDecode(token);
        return user;
    } catch (error) {
        return null;
    }
}

export default{
    login,
    logout,
    getCurrentUser
}