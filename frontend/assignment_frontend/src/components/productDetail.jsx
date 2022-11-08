import React, { Component, useState } from 'react';
import '../CSS/productDetail.css';
import { useParams, useLocation } from "react-router-dom";
import {useDispatch} from "react-redux"
import cartSystem, { addToCart } from '../redux/cartSystem';
import { ToastContainer, toast } from 'react-toastify';
// import {addToCart} from '../service/addToCart'



const ProductDetail = () => {
    const location = useLocation();
    const data = location.state;
    const dispatch = useDispatch();
    const[orderDetail,setOrderDetail] = useState({...data,quantity:1,total:data.price})
    const[order,setOrder] = useState({product:data,quantity:1, total:data.price})
    const[quantity,setQuantity] = useState(1);

    const hanldeAddToCart = (e) =>{

        console.log("Adding");
        console.log("Order: " , order);
        dispatch(addToCart(order));
    }


    const quantityAction = (action) =>{
        let currentQuantity = quantity;
        if(action=="increment"){
            setQuantity(++currentQuantity);
            if(quantity>=10){
                setQuantity(10);
            }
            // setOrderDetail({
            //     ...data,
            //     quantity: currentQuantity,
            //     total:data.price*currentQuantity
            // })
            setOrder({
                product:data,
                quantity:currentQuantity,
                total:data.price*currentQuantity
            })
            
        }
        if(action=="decrement"){
            setQuantity(--currentQuantity);
            if(currentQuantity<=1){
                setQuantity(1);
            }
            // setOrderDetail({
            //     ...data,
            //     quantity: currentQuantity,
            //     total:data.price*currentQuantity
            // })
            setOrder({
                product:data,
                quantity:currentQuantity,
                total:data.price*currentQuantity
            })
        }
    }

   
    return (
        <React.Fragment>
            <ToastContainer/>
            <body>
                <div id="app">
                    <div class="container container-space">
                        <div class="row">
                            <div class="col-md-6">
                                <img class="img-fluid" name="mainImg" src={ data.images.slice(0, 1).map(url => url.url) } alt="" />
                                <div class="product-thumbnails">
                                    <ul>
                                        <li v-for="(image, index) in productImages" class="[activeClass == index ? 'thumbnail-active': '']" key="index">
                                            {data.images.slice(1,4).map((image, index) => 
                                                <img name={index} className='mr-1' src={image.url} alt="" />
                                                )}
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <h1 class="my-4">{ data.name } - { data.price }</h1>
                                <h3 class="my-3">
                                    <div class="btn-group">
                                        <button type="button" onClick={()=>quantityAction("decrement")} class="btn btn-success btn-circle">-</button>
                                        <input type="number"  name='quantity' placeholder='Quantity' value={quantity} min="1" max="10" class="btn btn-outline-success p-1"/>
                                        <button type="button" onClick={()=>quantityAction("increment")} class="btn btn-success btn-circle ">+</button>
                                    </div>
                                </h3>
                                <h4 v-if="stockAvailability">Category: { data.categories }</h4>
                                {/* <h4 v-else="stockAvailability">Oops!! Out Of Stock</h4> */ }
                                <h3 class="my-3">Details</h3>
                                <ul v-for="detail in productChecks">
                                    <li>{ data.description }</li>
                                </ul>
                                <button type="button" 
                                class="btn btn-outline-success btn-lg btn-block btn-custom-color"
                                onClick={hanldeAddToCart}
                                >
                                    ADD TO CART
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

            </body >

        </React.Fragment >
    );
}

export default ProductDetail;