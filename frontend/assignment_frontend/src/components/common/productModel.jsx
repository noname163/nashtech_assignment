import React, { Component } from 'react';

const ProductModel = () => {

    return (
        <React.Fragment>
            <div class="modal" id="myModal">
                <div class="modal-dialog">
                    <form action="/products/insertProduct" method="post"
                        modelattribute="product" enctype="multipart/form-data">
                        <div class="modal-content">

                            <div class="modal-header">
                                <h4 class="modal-title">Create Product</h4>
                                <a href="/">Home</a>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>


                            <div class="modal-body">
                                <div class="row form-group">
                                    <div class="col col-md-5">
                                        <figure class="snip1336">
                                            <figcaption>
                                                <div>
                                                    <img src="/images/banner1 (1).jpg" alt="profile-sample4" class="profile img-fluid"
                                                        id="thumbnail" />
                                                </div><br />
                                                <div class="custom-file mt-3">
                                                    <input name="avatar" type="file" class="custom-file-input" id="fileImage" accept=".png,.jpg" />
                                                    <label class="custom-file-label" for="customFile">Choose file</label>
                                                </div>
                                            </figcaption>
                                        </figure>
                                    </div>
                                    <div class="col col-md-6 form-group" id="modelMainBody">
                                        <div>
                                            <label for="product">Product Name</label>
                                            <input type="text" name="name" class="form-control" id="product" aria-describedby="productHelp"
                                                placeholder="Enter product name" />
                                        </div>
                                        <br />
                                        <div class="dropdown form-group">
                                            <label for="dropdownMenuButton">Choose Categories: </label>
                                            <select name="category" class="btn btn-secondary dropdown-toggle" type="button"
                                                id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <div >
                                                    <option  ></option>
                                                </div>
                                            </select>
                                        </div>
                                        <div>
                                            <label for="expiryDate">Number Of Expiry Date</label><br />
                                            <div class="row" id="expiryDiv">
                                                <p class="mathBtn" id="mathBtn" onclick="decrement()">-</p>
                                                <input class="col col-md-8" type="number" name="expiryDate" id="expiryDate"
                                                    placeholder="Expiry date" />
                                                <p class="mathBtn" onclick="increment()">+</p>
                                            </div>
                                        </div>
                                        <br />
                                        <div>
                                            <textarea name="" id="" cols="30" rows="5"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer btn btn-group">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                                    <button type="submit" class="btn btn-success" value="submit">Save</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </React.Fragment>
    )
}

export default ProductModel;