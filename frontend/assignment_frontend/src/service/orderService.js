import config from "../config.json"
import http from "../service/httpService"
import { getCategories } from "./dataService";



export async function getOrder(email){
    let accessToken = localStorage.getItem("Access-Token");
    const options = {
        headers: {
            Authorizations: "Bearer "+accessToken
        }
    };
    return http.get("http://localhost:8080/add-to-cart/get-order/"+email,options)
}

export default getOrder