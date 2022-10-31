
import config from "../config.json"
import http from "../service/httpService"

const api = config.apiEndpoint + "/products";
export async function getProductData(){
    const { data: posts } =  await http.post(api);
    return posts;

}

export async function getCategories(){
    const{data:cate} =await http.get(api+"/categories");
    return cate;
}

export default{
    getProductData,
    getCategories
}