import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { toast } from 'react-toastify';
import "../CSS/cart.css";
import { changeQuantity, removeFromCart } from '../redux/cartSystem';
import addOrder from '../service/addToCartService';
import { getCurrentUser } from './../service/authenService';

const Cart = () => {
    const cart = useSelector((state) => state.cart).cartItems;
    const dispatch = useDispatch();
    const user = getCurrentUser();
    console.log("User: " +user.sub);
    const handleQuantity = (item, e) => {
        console.log("Item ", item);
        console.log("Current Quantity: " + e.target.value);
        let newItem = { ...item };
        newItem.quantity = e.target.value;
        dispatch(changeQuantity(newItem));
    }
    const handleDelete = (item) => {
        dispatch(removeFromCart(item))
    }
    const handelCheckout = async () => {
        try {
            await addOrder(cart,user.sub);
            localStorage.removeItem("cartItems")
            toast.success("Shopping Completed.")
            window.location.reload()
        } catch (error) {
            console.log("Error: " ,error.response.data.message);
            toast.error(error.response.data.message);
        }
    }
    console.log("Cart ", cart)
    return (
        <React.Fragment>
            <div class="container">
                { cart.length === 0 ? (
                    <div>
                        <h1>Your Cart Is Current Empty</h1>
                    </div>
                ) : <></> }
                <table id="cart" class="table table-hover table-condensed">
                    <thead>
                        <tr>
                            <th style={ { width: "50%" } }>Product</th>
                            <th style={ { width: "10%" } }>Price</th>
                            <th style={ { width: "8%" } }>Quantity</th>
                            <th style={ { width: "22%" } } class="text-center">Subtotal</th>
                            <th style={ { width: "10%" } }></th>
                        </tr>
                    </thead>
                    <tbody>
                        { cart.map(item =>
                            <tr>
                                <td data-th="Product">
                                    <div class="row">
                                        <div class="col-sm-2 hidden-xs">
                                            <img src={ item.product.images.slice(0, 1).map(image => image.url) } alt="..." class="img-responsive" style={ { width: '5em' } } />
                                        </div>
                                        <div class="col-sm-10">
                                            <h4 class="nomargin">{ item?.product?.name }</h4>
                                            <p>{ item?.product?.description }</p>
                                        </div>
                                    </div>
                                </td>
                                <td data-th="Price">{ item?.price }</td>
                                <td data-th="Quantity">
                                    <input type="number"
                                        class="form-control text-center"
                                        name="quantity"
                                        onChange={ (e) => handleQuantity(item, e) }
                                        value={ item?.quantity } />
                                </td>
                                <td data-th="Subtotal" class="text-center">{ item?.total }</td>
                                <td class="actions group-btn" data-th="">
                                    <button class="btn btn-info btn-sm">Refresh</button>
                                    <button class="btn btn-danger btn-sm" onClick={ () => handleDelete(item) }>Delete</button>
                                </td>
                            </tr>
                        ) }
                    </tbody>
                    <tfoot>
                        <tr class="visible-xs">
                            <td class="text-center"><strong>Total 1.99</strong></td>
                        </tr>
                        <tr>
                            <td><a href="#" class="btn btn-warning"><i class="fa fa-angle-left"></i> Continue Shopping</a></td>
                            <td colspan="2" class="hidden-xs"></td>
                            <td class="hidden-xs text-center"><strong>Total $1.99</strong></td>
                            <td><a href="#" class="btn btn-success btn-block" onClick={ handelCheckout } >Checkout <i class="fa fa-angle-right"></i></a></td>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </React.Fragment>
    )
}

export default Cart