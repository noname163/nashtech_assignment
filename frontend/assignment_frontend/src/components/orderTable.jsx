import React, { useEffect, useState } from 'react';
import { toast, ToastContainer } from 'react-toastify';
import "../CSS/cart.css";
import { acceptOrder, deliveryOrder, getAllOrder } from '../service/orderService';
import { getCurrentUser } from './../service/authenService';
import { swal } from 'sweetalert';

const OrderTable = () => {
    const [cart, setCart] = useState([]);
    const [button, setButton] = useState("Accept");
    const user = getCurrentUser();
    console.log("User: " + user.sub)
    const getOrders = async () => {
        try {
            console.log("Get Data")
            const { data: response } = await getAllOrder();
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
    const acceptHanlde = async (status,id) => {
        console.log("Changing");
        try {
            if (status == "PENDING") {
                await acceptOrder(id);
                setButton("Delivery");
                toast.success("Change Status to Accept");
                window.location.reload()
            }
            if (status == "ACCEPT") {
                await deliveryOrder(id);
                setButton("");
                toast.success("Change Status to Delivery");
                window.location.reload()
            }
        } catch (error) {
            // toast.error(error.response.message)
            console.log("Error")
        }
    }
    return (
        <React.Fragment>
            <ToastContainer/>
            <div class="container">
                { cart.length === 0 ? (
                    <div>
                        <h1>Your Cart Is Current Empty</h1>
                    </div>
                ) : <></> }
                <table id="cart" class="table table-hover table-condensed">
                    <thead>
                        <tr>
                            <th style={ { width: "2%", textAlign: "center" } }>OrderId</th>
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
                                    <p class="text-center">{ item?.id }</p>
                                </td>
                                <td data-th="Price">{ item?.orderDate }</td>
                                <td class="text-center" >
                                    { item.deliveryDate == null ? "Not Available" : item?.deliveryDate }
                                </td>
                                <td data-th="Subtotal" class="text-center">{ item?.status }</td>
                                <td class="actions group-btn" data-th="">
                                    { button == "" || item?.status=="DELIVERY" || item?.status =="SUCCESS"  ? "" : <button class="btn btn-info btn-sm" onClick={ () => acceptHanlde(item?.status,item?.id) }>{ button}</button> }
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

export default OrderTable