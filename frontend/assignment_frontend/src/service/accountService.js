import config from "../config.json"
import http from "../service/httpService"

const apiEndpoint = config.apiEndpoint;
console.log("Api: " + apiEndpoint);

export function register(user){
    http.post(apiEndpoint+"/register",{
        phoneNumber:user.phoneNumber,
        fullName: user.fullName,
        email: user.email,
        password: user.password,
        // avatar:user.avatar
    })
    // http.post(apiEndpoint+"/register",{
    //     phoneNumber:"0354394530",
    //     fullName: "test",
    //     email: "test@gmail.com",
    //     username: "user.username",
    //     password: "user.password"
    // });
}