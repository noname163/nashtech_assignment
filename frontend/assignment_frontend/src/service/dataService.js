
import config from "../config.json"
import http from "../service/httpService"
import { async } from './productService';

const api = config.apiEndpoint + "/products";
export async function getProductData(){
    const { data: posts } =  await http.post(api);
    return posts;

}

export async function getCategories(){
    const{data:cate} =await http.get(api+"/categories");
    return cate;
}
export async function getProductAdmin(){
    const { data: posts } =  await http.post('http://localhost:8080/admin/product');
    return posts;
}

export default{
    getProductData,
    getCategories,
    getProductAdmin
}