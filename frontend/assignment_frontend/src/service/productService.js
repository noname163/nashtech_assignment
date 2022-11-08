import config from "../config.json"
import http from "../service/httpService"
import { getCategories } from "./dataService";



export async function addNewProduct(product){
    let accessToken = localStorage.getItem("Access-Token");
    const options = {
        headers: {
            Authorizations: "Bearer "+accessToken
        }
    };
    return http.post("http://localhost:8080/admin/product",{
        name: product.name,
        price:product.price,
        categories: product.categories,
        description: product.des,
        images:product.images
    },options)
}
export async function updateProduct(product){
    let accessToken = localStorage.getItem("Access-Token");
    const options = {
        headers: {
            Authorizations: "Bearer "+accessToken
        }
    };
    return http.put("http://localhost:8080/admin/product/update",{
        name: product.name,
        price:product.price,
        categories: product.categories,
        description: product.description,
        images:product.images
    },options)
}
export async function newCategory(category){
    let accessToken = localStorage.getItem("Access-Token");
    const options = {
        headers: {
            Authorizations: "Bearer "+accessToken
        }
    };
    return http.post("http://localhost:8080/categories",{
        name: category.name,
        description: category.description
    },options)
}

export async function deleteProduct(id){
    let accessToken = localStorage.getItem("Access-Token");
    const options = {
        headers: {
            Authorizations: "Bearer "+accessToken
        }
    };
    return http.delete("http://localhost:8080/admin/product/"+id,options)
}


export default{
    addNewProduct,updateProduct, newCategory
}