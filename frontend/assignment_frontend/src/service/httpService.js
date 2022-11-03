import axios from "axios";
import { log } from "joi-browser";
import {toast} from 'react-toastify';
import logService from "./logService"

axios.interceptors.response.use(null, error => {
    const expectedError =
        error.response && error.response.status >= 400 &&
        error.response.status <= 500;
    if(!expectedError){
        logService.log(error);
        toast.error("An unexpected error occurred.");
    }
    return Promise.reject(error);
});

export default{
    get: axios.get,
    post: axios.post,
    postForm: axios.postForm,
    put: axios.put,
    delete: axios.delete,
    patch: axios.patch
}