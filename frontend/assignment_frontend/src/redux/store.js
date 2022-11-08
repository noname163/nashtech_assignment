import { configureStore } from "@reduxjs/toolkit";

import cartSystem from "./cartSystem";
const store = configureStore({
    reducer:{
        cart:cartSystem
    }
})

export default store;