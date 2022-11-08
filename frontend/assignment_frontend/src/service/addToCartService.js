import config from "../config.json"
import http from "../service/httpService"
import { getCategories } from "./dataService";



export async function addOrder(orders,email){
    let accessToken = localStorage.getItem("Access-Token");
    const options = {
        headers: {
            Authorizations: "Bearer "+accessToken
        }
    };
    return http.post("http://localhost:8080/add-to-cart",{
        email:email,
       orderDetails:orders
    },options)
}

export default addOrder