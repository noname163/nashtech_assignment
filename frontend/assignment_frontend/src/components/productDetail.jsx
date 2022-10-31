import React, { Component } from 'react';
import '../CSS/productDetail.css';
import {useParams, useLocation} from "react-router-dom";



const ProductDetail = () =>{
    const location = useLocation();
    const data = location.state;

    return (
        <React.Fragment>
            <section id="services" class="services section-bg" >
                <div class="container-fluid">
                    {/* <div class="col-sm-12 text-center mb-4">
                        <a class="btn btn-primary" target="_blank" href="http://paypal.me/skd1996"> Donate Now <i class="fa fa-dollar"></i></a>
                    </div> */}
                    <div class="row row-sm">
                        <div class="col-md-6 _boxzoom">
                            <div class="zoom-thumb">
                                <ul class="piclist">
                                    {data.images.map(image=>
                                        <li><img src={image.url} alt="productImage" /></li>
                                    )}
                                    
                                    <li><img src="https://s.fotorama.io/2.jpg" alt="" /></li>
                                    <li><img src="https://s.fotorama.io/3.jpg" alt="" /></li>
                                </ul>
                            </div>
                            <div class="_product-images">
                                <div class="picZoomer">
                                    <img class="my_img" src="/images/test.jpg" alt="" />
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="_product-detail-content">
                                <p class="_p-name"> Milton Bottle </p>
                                <div class="_p-price-box">
                                    <div class="p-list">
                                        <span> M.R.P. : <i class="fa fa-inr"></i> <del> 1399  </del>   </span>
                                        <span class="price"> {data.price} VND</span>
                                    </div>
                                    <div class="_p-add-cart">
                                        <div class="_p-qty">
                                            <span>Add Quantity</span>
                                            <div class="value-button decrease_" id="" value="Decrease Value">-</div>
                                            <input type="number" name="qty" id="number" value="1" />
                                            <div class="value-button increase_" id="" value="Increase Value">+</div>
                                        </div>
                                    </div>
                                    <div class="_p-features">
                                        <span> Description About this product: </span>
                                        {data.description}
                                    </div>
                                    <form action="" method="post" accept-charset="utf-8">
                                        <ul class="spe_ul"></ul>
                                        <div class="_p-qty-and-cart">
                                            <div class="_p-add-cart">
                                                <button class="btn-theme btn buy-btn" tabindex="0">
                                                    <i class="fa fa-shopping-cart"></i> Buy Now
                                                </button>
                                                <button class="btn-theme btn btn-success" tabindex="0">
                                                    <i class="fa fa-shopping-cart"></i> Add to Cart
                                                </button>
                                                <input type="hidden" name="pid" value="18" />
                                                <input type="hidden" name="price" value="850" />
                                                <input type="hidden" name="url" value="" />
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section class="sec bg-light">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-12 title_bx">
                            <h3 class="title"> Recent Post   </h3>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 list-slider mt-4">
                            <div class="owl-carousel common_wd  owl-theme" id="recent_post">
                                <div class="item">
                                    <div class="sq_box shadow">
                                        <div class="pdis_img">
                                            <span class="wishlist">
                                                <a alt="Add to Wish List" title="Add to Wish List" href="javascript:void(0);"> <i class="fa fa-heart"></i></a>
                                            </span>
                                            <a href="#">
                                                <img src="https://ucarecdn.com/05f649bf-b70b-4cf8-90f7-2588ce404a08/-/resize/680x/" />
                                            </a>
                                        </div>
                                        <h4 class="mb-1"> <a href="details.php"> Milton Bottle </a> </h4>
                                        <div class="price-box mb-2">
                                            <span class="price"> Price <i class="fa fa-inr"></i> 200 </span>
                                            <span class="offer-price"> Offer Price <i class="fa fa-inr"></i> 120 </span>
                                        </div>
                                        <div class="btn-box text-center">
                                            <a class="btn btn-sm" href="javascript:void(0);"> <i class="fa fa-shopping-cart"></i> Add to Cart </a>
                                        </div>
                                    </div>
                                </div>
                                <div class="item">
                                    <div class="sq_box shadow">
                                        <div class="pdis_img">
                                            <span class="wishlist">
                                                <a alt="Add to Wish List" title="Add to Wish List" href="javascript:void(0);"> <i class="fa fa-heart"></i></a>
                                            </span>
                                            <a href="#">
                                                <img src="https://ucarecdn.com/05f649bf-b70b-4cf8-90f7-2588ce404a08/-/resize/680x/" />
                                            </a>
                                        </div>
                                        <h4 class="mb-1"> <a href="details.php"> Milton Bottle </a> </h4>
                                        <div class="price-box mb-2">
                                            <span class="price"> Price <i class="fa fa-inr"></i> 200 </span>
                                            <span class="offer-price"> Offer Price <i class="fa fa-inr"></i> 120 </span>
                                        </div>
                                        <div class="btn-box text-center">
                                            <a class="btn btn-sm" href="javascript:void(0);"> <i class="fa fa-shopping-cart"></i> Add to Cart </a>
                                        </div>
                                    </div>
                                </div>
                                <div class="item">
                                    <div class="sq_box shadow">
                                        <div class="pdis_img">
                                            <span class="wishlist">
                                                <a alt="Add to Wish List" title="Add to Wish List" href="javascript:void(0);"> <i class="fa fa-heart"></i></a>
                                            </span>
                                            <a href="#">
                                                <img src="https://ucarecdn.com/05f649bf-b70b-4cf8-90f7-2588ce404a08/-/resize/680x/" />
                                            </a>
                                        </div>
                                        <h4 class="mb-1"> <a href="#"> Milton Bottle </a> </h4>
                                        <div class="price-box mb-2">
                                            <span class="price"> Price <i class="fa fa-inr"></i> 200 </span>
                                            <span class="offer-price"> Offer Price <i class="fa fa-inr"></i> 120 </span>
                                        </div>
                                        <div class="btn-box text-center">
                                            <a class="btn btn-sm" href="javascript:void(0);"> <i class="fa fa-shopping-cart"></i> Add to Cart </a>
                                        </div>
                                    </div>
                                </div>
                                <div class="item">
                                    <div class="sq_box shadow">
                                        <div class="pdis_img">
                                            <span class="wishlist">
                                                <a alt="Add to Wish List" title="Add to Wish List" href="javascript:void(0);"> <i class="fa fa-heart"></i></a>
                                            </span>
                                            <a href="#">
                                                <img src="https://ucarecdn.com/05f649bf-b70b-4cf8-90f7-2588ce404a08/-/resize/680x/" />
                                            </a>
                                        </div>
                                        <h4 class="mb-1"> <a href="#"> Milton Bottle </a> </h4>
                                        <div class="price-box mb-2">
                                            <span class="price"> Price <i class="fa fa-inr"></i> 200 </span>
                                            <span class="offer-price"> Offer Price <i class="fa fa-inr"></i> 120 </span>
                                        </div>
                                        <div class="btn-box text-center">
                                            <a class="btn btn-sm" href="javascript:void(0);"> <i class="fa fa-shopping-cart"></i> Add to Cart </a>
                                        </div>
                                    </div>
                                </div>
                                <div class="item">
                                    <div class="sq_box shadow">
                                        <div class="pdis_img">
                                            <span class="wishlist">
                                                <a alt="Add to Wish List" title="Add to Wish List" href="javascript:void(0);"> <i class="fa fa-heart"></i></a>
                                            </span>
                                            <a href="#">
                                                <img src="https://ucarecdn.com/05f649bf-b70b-4cf8-90f7-2588ce404a08/-/resize/680x/" />
                                            </a>
                                        </div>
                                        <h4 class="mb-1"> <a href="details.php"> Milton Bottle </a> </h4>
                                        <div class="price-box mb-2">
                                            <span class="price"> Price <i class="fa fa-inr"></i> 200 </span>
                                            <span class="offer-price"> Offer Price <i class="fa fa-inr"></i> 120 </span>
                                        </div>
                                        <div class="btn-box text-center">
                                            <a class="btn btn-sm" href="javascript:void(0);"> <i class="fa fa-shopping-cart"></i> Add to Cart </a>
                                        </div>
                                    </div>
                                </div>
                                <div class="item">
                                    <div class="sq_box shadow">
                                        <div class="pdis_img">
                                            <span class="wishlist">
                                                <a alt="Add to Wish List" title="Add to Wish List" href="javascript:void(0);"> <i class="fa fa-heart"></i></a>
                                            </span>
                                            <a href="#">
                                                <img src="https://ucarecdn.com/05f649bf-b70b-4cf8-90f7-2588ce404a08/-/resize/680x/" />
                                            </a>
                                        </div>
                                        <h4 class="mb-1"> <a href="details.php"> Milton Bottle </a> </h4>
                                        <div class="price-box mb-2">
                                            <span class="price"> Price <i class="fa fa-inr"></i> 200 </span>
                                            <span class="offer-price"> Offer Price <i class="fa fa-inr"></i> 120 </span>
                                        </div>
                                        <div class="btn-box text-center">
                                            <a class="btn btn-sm" href="javascript:void(0);"> <i class="fa fa-shopping-cart"></i> Add to Cart </a>
                                        </div>
                                    </div>
                                </div>
                                <div class="item">
                                    <div class="sq_box shadow">
                                        <div class="pdis_img">
                                            <span class="wishlist">
                                                <a alt="Add to Wish List" title="Add to Wish List" href="javascript:void(0);"> <i class="fa fa-heart"></i></a>
                                            </span>
                                            <a href="#">
                                                <img src="https://ucarecdn.com/05f649bf-b70b-4cf8-90f7-2588ce404a08/-/resize/680x/" />
                                            </a>
                                        </div>
                                        <h4 class="mb-1"> <a href="details.php"> Milton Bottle </a> </h4>
                                        <div class="price-box mb-2">
                                            <span class="price"> Price <i class="fa fa-inr"></i> 200 </span>
                                            <span class="offer-price"> Offer Price <i class="fa fa-inr"></i> 120 </span>
                                        </div>
                                        <div class="btn-box text-center">
                                            <a class="btn btn-sm" href="javascript:void(0);"> <i class="fa fa-shopping-cart"></i> Add to Cart </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

        </React.Fragment>
    );
}

export default ProductDetail;