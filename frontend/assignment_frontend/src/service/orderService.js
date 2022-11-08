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

export async function getAllOrder(){
    let accessToken = localStorage.getItem("Access-Token");
    const options = {
        headers: {
            Authorizations: "Bearer "+accessToken
        }
    };
    return http.get("http://localhost:8080/admin/orders",options)
}
export async function acceptOrder(orderId){
    let accessToken = localStorage.getItem("Access-Token");
    const options = {
        headers: {
            Authorizations: "Bearer "+accessToken
        }
    };
    return http.patch("http://localhost:8080/admin/orders/accept-order/"+orderId,orderId,options)
}
export async function deliveryOrder(orderId){
    let accessToken = localStorage.getItem("Access-Token");
    const options = {
        headers: {
            Authorizations: "Bearer "+accessToken
        }
    };
    return http.patch("http://localhost:8080/admin/orders/delivery-order/"+orderId,orderId,options)
}
export async function confirmOrder(orderId){
    let accessToken = localStorage.getItem("Access-Token");
    const options = {
        headers: {
            Authorizations: "Bearer "+accessToken
        }
    };
    return http.patch("http://localhost:8080/add-to-cart/confirm-order/"+orderId,orderId,options)
}
export async function getOrderDetailById(orderId){
    let accessToken = localStorage.getItem("Access-Token");
    const options = {
        headers: {
            Authorizations: "Bearer "+accessToken
        }
    };
    return http.get("http://localhost:8080/add-to-cart/get-order-detail/"+orderId,options)
}

export default {
    getOrder,
    getAllOrder,
    acceptOrder,
    deliveryOrder,
    confirmOrder
}