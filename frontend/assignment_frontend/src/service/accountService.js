import config from "../config.json"
import http from "../service/httpService"

const apiEndpoint = config.apiEndpoint;
console.log("Api: " + apiEndpoint);

export async function register(user){
    return http.post(apiEndpoint+"/register",{
        phoneNumber:user.phoneNumber,
        fullName: user.fullName,
        email: user.email,
        password: user.password,
    })
}

export default {
    register
}