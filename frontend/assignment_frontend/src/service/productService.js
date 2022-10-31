import config from "../config.json"
import http from "../service/httpService"



export function addNewProduct(product){
    let accessToken = localStorage.getItem("Access-Token");
    const options = {
        headers: {
            Authorizations: "Bearer "+accessToken
        }
    };
    http.post("http://localhost:8080/admin/product",{
        name: product.name,
        price:product.price,
        categories: product.categories,
        description: product.des
    },options)
}

export default{
    addNewProduct
}