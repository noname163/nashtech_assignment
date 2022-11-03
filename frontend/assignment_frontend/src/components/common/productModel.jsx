import React, { Component } from 'react';

const ProductModel = () => {

    return (
        <React.Fragment>
            <div className="modal" id="myModal">
                <div className="modal-dialog">
                    <form action="/products/insertProduct" method="post"
                        modelattribute="product" enctype="multipart/form-data">
                        <div className="modal-content">

                            <div className="modal-header">
                                <h4 className="modal-title">Create Product</h4>
                                <a href="/">Home</a>
                                <button type="button" className="close" data-dismiss="modal">&times;</button>
                            </div>


                            <div className="modal-body">
                                <div className="row form-group">
                                    <div className="col col-md-5">
                                        <figure className="snip1336">
                                            <figcaption>
                                                <div>
                                                    <img src="/images/banner1 (1).jpg" alt="profile-sample4" className="profile img-fluid"
                                                        id="thumbnail" />
                                                </div><br />
                                                <div className="custom-file mt-3">
                                                    <input name="avatar" type="file" className="custom-file-input" id="fileImage" accept=".png,.jpg" />
                                                    <label className="custom-file-label" for="customFile">Choose file</label>
                                                </div>
                                            </figcaption>
                                        </figure>
                                    </div>
                                    <div className="col col-md-6 form-group" id="modelMainBody">
                                        <div>
                                            <label for="product">Product Name</label>
                                            <input type="text" name="name" className="form-control" id="product" aria-describedby="productHelp"
                                                placeholder="Enter product name" />
                                        </div>
                                        <br />
                                        <div className="dropdown form-group">
                                            <label for="dropdownMenuButton">Choose Categories: </label>
                                            <select name="category" className="btn btn-secondary dropdown-toggle" type="button"
                                                id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <div >
                                                    <option  ></option>
                                                </div>
                                            </select>
                                        </div>
                                        <div>
                                            <label for="expiryDate">Number Of Expiry Date</label><br />
                                            <div className="row" id="expiryDiv">
                                                <p className="mathBtn" id="mathBtn" onclick="decrement()">-</p>
                                                <input className="col col-md-8" type="number" name="expiryDate" id="expiryDate"
                                                    placeholder="Expiry date" />
                                                <p className="mathBtn" onclick="increment()">+</p>
                                            </div>
                                        </div>
                                        <br />
                                        <div>
                                            <textarea name="" id="" cols="30" rows="5"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div className="modal-footer btn btn-group">
                                    <button type="button" className="btn btn-danger" data-dismiss="modal">Close</button>
                                    <button type="submit" className="btn btn-success" value="submit">Save</button>
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