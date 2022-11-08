import React, { useEffect, useState } from 'react';
import { toast, ToastContainer } from 'react-toastify';
import Rating from '@material-ui/lab/Rating';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import "../CSS/cart.css";
import { acceptOrder, deliveryOrder, getAllOrder, getOrderDetailById } from '../service/orderService';
import { getCurrentUser } from './../service/authenService';
import { swal } from 'sweetalert';
import { useParams } from 'react-router-dom';


const OrderDetail = () => {
    const [cart, setCart] = useState([]);
    const [button, setButton] = useState("Accept");
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
    const ratingHandel = () => {

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
                                    { button == "" || item?.status == "DELIVERY" || item?.status == "SUCCESS" ? "" : <button class="btn btn-info btn-sm" onClick={ () => ratingHandel(item?.status, item?.id) }>{ button }</button> }
                                    <button class="btn btn-danger btn-sm" >Cancel</button>
                                </td>
                            </tr>
                        ) }
                    </tbody>
                    {/* <tfoot>
                        <tr class="visible-xs">
                            <td class="text-center"><strong>Total 1.99</strong></td>
                        </tr>
                        <tr>
                            <td><a href="#" class="btn btn-warning"><i class="fa fa-angle-left"></i> Continue Shopping</a></td>
                            <td colspan="2" class="hidden-xs"></td>
                            <td class="hidden-xs text-center"><strong>Total $1.99</strong></td>
                            <td><a href="#" class="btn btn-success btn-block" >Checkout <i class="fa fa-angle-right"></i></a></td>
                        </tr>
                    </tfoot> */}
                </table>
            </div>
        </React.Fragment>
    )
}

export default OrderDetail