import config from "../config.json"
import http from "../service/httpService"
import { getCategories } from "./dataService";



export async function rating(id,star){
    let accessToken = localStorage.getItem("Access-Token");
    const options = {
        headers: {
            Authorizations: "Bearer "+accessToken
        }
    };
    return http.patch(`http://localhost:8080/rate-product/${id}/${star}`,id,options)
}

export default rating