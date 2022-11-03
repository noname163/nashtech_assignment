import config from "../config.json"
import http from "../service/httpService"

export function uploadMultibleImage(image){
    const {urls} =http.postForm(config.apiEndpoint+"/image/images",{image});
    return urls;
}
export default{
    uploadMultibleImage
}