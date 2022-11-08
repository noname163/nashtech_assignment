import 'bootstrap/dist/css/bootstrap.css';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import store from './redux/store';
import reportWebVitals from './reportWebVitals';
import logService from "./service/logService";
import { configureStore } from '@reduxjs/toolkit';
import cartSystem from './redux/cartSystem';
import cartSlice from "./service/addToCart"


logService.init();

// const store = configureStore({
//   reducer:{
//     cart: cartSlice,
//   }
 
// })


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Provider store={store}>
        <App />
      </Provider>
    </BrowserRouter>
  </React.StrictMode>
);


reportWebVitals();
