import Rating from '@material-ui/lab/Rating';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import "../CSS/cart.css";
import { getOrderDetailById } from '../service/orderService';
import rating from '../service/ratingService';
import { getCurrentUser } from './../service/authenService';


const OrderDetail = () => {
    const [cart, setCart] = useState([]);
    const [style, setStyle] = useState("");
    const user = getCurrentUser();
    const [ratingValue, setRatingValue] = React.useState(0);
    console.log("User: " + user.sub)
    let { id } = useParams();
    const getOrders = async () => {
        try {
            console.log("Get Data ID: " + id)
            const { data: response } = await getOrderDetailById(id);
            console.log("Get Data")
            setCart(response);
        } catch (error) {
            console.log("Error: " + error.response.data.message)
            toast.error(error.response.data.message)
        }
    }
    useEffect(() => {
        getOrders();

    }, []);
    console.log("Cart ", cart)
    console.log("Rating: ", ratingValue)
    const hanldeRating = async (id, star) => {
        await rating(id,star);
        console.log("Rating Success");
        toast.success("Rating Success");
        setStyle("none");
    }

    return (
        <React.Fragment>
            <ToastContainer />
            <div class="container">
                { cart.length === 0 ? (
                    <div>
                        <h1>Your Cart Is Current Empty</h1>
                    </div>
                ) : <></> }
                <table id="cart" class="table table-hover table-condensed">
                    <thead>
                        <tr>
                            <th style={ { width: "35%", textAlign: "center" } }>Product Name</th>
                            <th style={ { width: "10%" } }>Order Date</th>
                            <th style={ { width: "10%" } } class="text-center">Delivery Date</th>
                            <th class="text-center" style={ { width: "18%" } }>Order Status</th>
                            <th style={ { width: "15%" } }></th>
                        </tr>
                    </thead>
                    <tbody>
                        { cart.map(item =>
                            <tr>
                                <td>
                                    <div class="row">
                                        <div class="col-sm-2 hidden-xs">
                                            <img src={ item.product.images.slice(0, 1).map(image => image.url) } alt="..." class="img-responsive" style={ { width: '5em' } } />
                                        </div>
                                        <div class="col-sm-5 ml-4">
                                            <h4 class="nomargin">{ item?.product?.name }</h4>
                                            <Rating
                                                name="Rating Label"
                                                value={ ratingValue }
                                                onChange={ (event, newValue) => {
                                                    setRatingValue(newValue);
                                                } }
                                            />
                                        </div>
                                    </div>
                                </td>
                                <td data-th="Price">{ item?.order?.orderDate }</td>
                                <td class="text-center" >
                                    { item?.order?.deliveryDate == null ? "Not Available" : item?.order?.deliveryDate }
                                </td>
                                <td data-th="Subtotal" class="text-center">{ item?.status }</td>
                                <td class="actions group-btn" data-th="">
                                    <button class="btn btn-info btn-sm" onClick={()=>hanldeRating(item?.id,ratingValue)} style={{display:style}} >Submit Rating</button>
                                </td>
                            </tr>
                        ) }
                    </tbody>
                </table>
            </div>
        </React.Fragment>
    )
}

export default OrderDetail