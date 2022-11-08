import { createSlice } from "@reduxjs/toolkit";
import { toast } from "react-toastify";

const initialState = {
    cartItems: localStorage.getItem("cartItems") ? JSON.parse(localStorage.getItem("cartItems")) : [],
    cartTotalQuantity: 0,
    cartTotalAmount: 0,
};

const cartSystem = createSlice({
    name: "cart",
    initialState,
    reducers: {
        addToCart(state, action) {
            const itemIndex = state.cartItems.findIndex((item) => item.product.id === action.payload.product.id);
            if (itemIndex >= 0) {
                state.cartItems[itemIndex].quantity += action.payload.quantity;
                state.cartItems[itemIndex].total = state.cartItems[itemIndex].quantity * state.cartItems[itemIndex].product.price;
                toast.info("Incerment quantity to " + state.cartItems[itemIndex].quantity)
            } else {
                toast.success("Add " + action.payload.quantity + " " + action.payload.product.name + " to your cart.")
                state.cartItems.push(action.payload);
            }
            localStorage.setItem("cartItems", JSON.stringify(state.cartItems))
        },
        removeFromCart(state, action) {
            const nextCartItems = state.cartItems.filter(
                item => item.product.id !== action.payload.product.id
            )
            state.cartItems = nextCartItems;
            localStorage.setItem("cartItems", JSON.stringify(state.cartItems));
            toast.error("Remove " + action.payload.product.name + " From Cart");
        },
        changeQuantity(state, action) {
            const itemIndex = state.cartItems.findIndex(
                item => item.product.id = action.payload.product.id
            )
            if (action.payload.quantity > 10) {
                toast.error("Quantity Cannot Lager Than 10");
                state.cartItems[itemIndex].quantity = 10;
                state.cartItems[itemIndex].total = action.payload.quantity * action.payload.product.price;
                localStorage.setItem("cartItems", JSON.stringify(state.cartItems));
            }
            if (action.payload.quantity  <= 0) {
                toast.error("Quantity Cannot Smaller Than 0");
                state.cartItems[itemIndex].quantity = 1;
                state.cartItems[itemIndex].total = action.payload.quantity * action.payload.product.price;
                localStorage.setItem("cartItems", JSON.stringify(state.cartItems));
            }
            else if(action.payload.quantity >0 && action.payload.quantity <10 ) {
                state.cartItems[itemIndex] = action.payload;
                state.cartItems[itemIndex].total = action.payload.quantity * action.payload.product.price;
                localStorage.setItem("cartItems", JSON.stringify(state.cartItems));
                toast.info("Change Quantity Of " + action.payload.product.name + " To " + action.payload.quantity);
            }
        }
    },
});

export const { addToCart, removeFromCart, changeQuantity } = cartSystem.actions;

export default cartSystem.reducer;


