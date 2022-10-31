import React, { Component } from 'react';
import axios from 'axios';
import Joi from 'joi-browser';
import From from './common/form';
import productService from '../service/productService.js'
class NewProduct extends From {
    state = {
        data: {
            name:"", price:"",categories:"",des:""
        },
        cates: [],
        errors:{}
    };
    schema = {
        name: Joi.string()
            .required()
            .label("Name"),
        price: Joi.string()
            .required()
            .label("Price"),
        categories: Joi.string()
            .required()
            .label("Categories"),
        des: Joi.string().required().label("Description"),

    };
    async componentDidMount() {
        const { data: cates } = await axios.get("http://localhost:8080/products/categories");
        this.setState({ cates });
    };
    doSubmit = async () => {
        
        try {
            console.log("Submiting");
            await productService.addNewProduct(this.state.data);
            console.log("Succesfull");
        } catch (ex) {
            console.log("Errors");
            if (ex.response && ex.response.status === 400) {
                console.log("errors");
                const errors = { ...this.state.errors };
                errors.email = ex.response.data.mess;
                console.log("Mess" + errors);
                this.setState({ errors });
            }
        }

    };
    chooseImage = () => {
        console.log("Click");
        document.getElementById('fileInput').click();
    };
    
    render() {
        return (
            <React.Fragment>
                <div class="container tm-mt-big tm-mb-big">
                    <div class="row">
                        <div class="col-xl-9 col-lg-10 col-md-12 col-sm-12 mx-auto" style={ { marginLeft: 'auto!important' } }>
                            <div class="tm-bg-primary-dark tm-block tm-block-h-auto" style={ { backgroundColor: '#435c70', minHeight: '1px', maxHeight: 'none', height: 'auto', padding: '40px' } }>
                                <div class="row" style={ { display: 'flex', flexWrap: 'wrap', marginRight: '-15px', marginLeft: -'15px' } }>
                                    <div class="col-12" style={ { flex: '0 0 100%', maxWidth: '100%' } }>
                                        <h2 class="tm-block-title d-inline-block" style={ { fontSize: '1.1rem', fontWeight: '700', color: '#fff', marginBottom: '30px' } }>Add Product</h2>
                                    </div>
                                </div>
                                <div class="row tm-edit-product-row" style={ { display: 'flex', flexWrap: 'wrap', marginRight: '-15px', marginLeft: -'15px' } }>
                                    <div class="col-xl-6 col-lg-6 col-md-12" style={ { flex: '0 0 50%', maxWidth: '50%' } }>
                                        <form action="" class="tm-edit-product-form" style={ { display: 'block', marginTop: '0em' }} onSubmit={ this.handleSubmit }>
                                            <div class="form-group mb-3" style={ { marginBottom: '1rem !important' } }>
                                                <label for="name">Product Name
                                                </label>
                                                { this.renderInput('name', 'Name','text,"form-control validate"') }
                                                
                                            </div>
                                            <div class="form-group mb-3" style={ { marginBottom: '1rem !important' } }>
                                                <label
                                                    for="description">
                                                    Description
                                                </label>
                                                {this.renderTextArea('des', "Description")}
                                            </div>
                                            <div class="form-group mb-3" style={ { marginBottom: '1rem !important' } }>
                                                    {this.renderSelect('categories','Categories',this.state.cates)}
                                            </div>
                                            <div class="row" style={ { display: 'flex', flexWrap: 'wrap', marginRight: '-15px', marginLeft: -'15px' } }>
                                                <div class="form-group mb-3 col-xs-12 col-sm-6">
                                                    <label
                                                        for="price">Price
                                                    </label>
                                                    { this.renderInput('price', 'Price','text,"form-control validate"') }
                                                </div>
                                                <div class="form-group mb-3 col-xs-12 col-sm-6">
                                                    <label
                                                        for="stock"
                                                    >Units In Stock
                                                    </label>
                                                    <input
                                                        id="stock"
                                                        name="stock"
                                                        type="text"
                                                        class="form-control validate"
                                                        required
                                                    />
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="col-xl-6 col-lg-6 col-md-12 mx-auto mb-4">
                                        <div class="tm-product-img-dummy mx-auto" style={ { maxWidth: '100%', height: '240px', display: 'flex', alignItems: 'center', justifyContent: 'center', color: '#fff', background: '#aaa' } }>
                                            <i
                                                class="fas fa-cloud-upload-alt tm-upload-icon"
                                                onclick={ this.chooseImage }
                                                style={ { background: '#455c71', width: '55px', height: '55px', borderRadius: '50%', textAlign: 'center', paddingTop: '15px', fontSize: '22px' } }
                                            ></i>
                                        </div>
                                        <div class="custom-file mt-3 mb-3">
                                            <input id="fileInput" type="file" style={{display:'none'}} />
                                            <input style={ { color: '#fff', backgroundColor: '#f5a623', border: '2px solid #f5a623', fontSize: '90%', fontWeight: '600' } }
                                                type="button"
                                                class="btn btn-primary btn-block mx-auto"
                                                value="UPLOAD PRODUCT IMAGE"
                                                onclick={ this.chooseImage }
                                            />
                                        </div>
                                    </div>
                                    <div class="col-12" onClick={this.handleSubmit}>
                                        {this.renderButton("ADD PRODUCT NOW","btn btn-primary btn-block text-uppercase"," color: '#fff', backgroundColor: '#f5a623', border: '2px solid #f5a623', fontSize: '90%', fontWeight: '600' ")}
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </React.Fragment >
        )
    }
}

export default NewProduct;