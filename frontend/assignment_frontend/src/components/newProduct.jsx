import React, { Component } from 'react';
import axios from 'axios';
import Joi from 'joi-browser';
import From from './common/form';
import productService from '../service/productService.js'
import { ToastContainer, toast } from 'react-toastify';
class NewProduct extends From {
    state = {
        data: {
            name: "", price: "", categories: "", des: "", images: []
        },
        file: null,
        cates: [],
        previewImage: null,
        errors: {}
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
        images: Joi.array().items()

    };
    async componentDidMount() {
        const { data: cates } = await axios.get("http://localhost:8080/products/categories");
        this.setState({ cates });
    };
    componentDidUpdate() {
        // console.log("FILE UPDATE: ", this.state.file);
        if (this.state.file != null) {
            let templeImage = URL.createObjectURL(this.state.file);
            let thumble = document.getElementById("thumble");
            let icon = document.getElementById("selectIcon");
            icon.style.display = "none";
            thumble.src = templeImage;
            thumble.style.display = "";
            document.getElementById("closeIcon").style.display = "";
            console.log("TMP IMAGE: " + templeImage);
        }
    }
    doSubmit = async () => {

        try {
            console.log("Submiting");
            if (this.state.data.images == "") {
                toast.error("Submit Image First.")
            }
            if (this.state.data.images != "") {
                await productService.addNewProduct(this.state.data);
                toast.success('Upload Product Success.');
                window.location="/product-admin";
            }
        } catch (ex) {
            console.log("Errors");
            if (ex.response && ex.response.status === 400) {
                console.log("errors");
                const errors = { ...this.state.errors };
                errors.email = ex.response.data.mess;
                toast.error(ex.response.data.mess)
                console.log("Mess" + errors);
                this.setState({ errors });
            }
        }

    };
    uploadImage = async () => {
        try {
            if (this.state.file != null) {
                const data = { ...this.state.data };
                let multipartFiles = this.state.file;
                let form = new FormData();
                form.append("multipartFiles", multipartFiles)
                let { data: url } = await axios.post("http://localhost:8080/image/images", form);
                data.images.push(url.toString());
                this.setState({ data });
                document.getElementById("uploadBtn").style.display = "none";
                toast.success('Upload Image Success.');
            }
            if(this.state.file==null){
                toast.error("Empty Image.");
            }
        } catch (error) {
            console.log(error);
            toast.error(error);
        }


    }
    removeTmpImg = () => {
        let thumble = document.getElementById("thumble");
        let icon = document.getElementById("selectIcon");
        icon.style.display = "";
        thumble.src = "";
        thumble.style.display = "none";
        document.getElementById("closeIcon").style.display = "none";
        this.setState({file:null});

    }
    imageChange = e => {
        this.setState({ file: e.target.files[0] })
    }

    render() {
        console.log("Datas: " + this.state.data.images[0]);
        return (
            <React.Fragment>
                <ToastContainer />
                <div className="container tm-mt-big tm-mb-big">
                    <div className="row">
                        <div className="col-xl-9 col-lg-10 col-md-12 col-sm-12 mx-auto" style={ { marginLeft: 'auto!important' } }>
                            <div className="tm-bg-primary-dark tm-block tm-block-h-auto" style={ { backgroundColor: '#435c70', minHeight: '1px', maxHeight: 'none', height: 'auto', padding: '40px' } }>
                                <div className="row" style={ { display: 'flex', flexWrap: 'wrap', marginRight: '-15px', marginLeft: -'15px' } }>
                                    <div className="col-12" style={ { flex: '0 0 100%', maxWidth: '100%' } }>
                                        <h2 className="tm-block-title d-inline-block" style={ { fontSize: '1.1rem', fontWeight: '700', color: '#fff', marginBottom: '30px' } }>Add Product</h2>
                                    </div>
                                </div>
                                <div className="row tm-edit-product-row" style={ { display: 'flex', flexWrap: 'wrap', marginRight: '-15px', marginLeft: -'15px' } }>
                                    <div className="col-xl-6 col-lg-6 col-md-12" style={ { flex: '0 0 50%', maxWidth: '50%' } }>
                                        <form action="" className="tm-edit-product-form" style={ { display: 'block', marginTop: '0em' } } onSubmit={ this.handleSubmit }>
                                            <div className="form-group mb-3" style={ { marginBottom: '1rem !important' } }>
                                                <label for="name">Product Name
                                                </label>
                                                { this.renderInput('name', 'Name', 'text,"form-control validate"') }

                                            </div>
                                            <div className="form-group mb-3" style={ { marginBottom: '1rem !important' } }>
                                                <label
                                                    for="description">
                                                    Description
                                                </label>
                                                { this.renderTextArea('des', "Description") }
                                            </div>
                                            <div className="form-group mb-3" style={ { marginBottom: '1rem !important' } }>
                                                { this.renderSelect('categories', 'Categories', this.state.cates) }
                                            </div>
                                            <div className="row" style={ { display: 'flex', flexWrap: 'wrap', marginRight: '-15px', marginLeft: -'15px' } }>
                                                <div className="form-group mb-3 col-xs-12 col-sm-6">
                                                    <label
                                                        for="price">Price
                                                    </label>
                                                    { this.renderInput('price', 'Price', 'text,"form-control validate"') }
                                                </div>
                                                <div className="form-group mb-3 col-xs-12 col-sm-6">
                                                    <label
                                                        for="stock"
                                                    >Units In Stock
                                                    </label>
                                                    <input
                                                        id="stock"
                                                        name="stock"
                                                        type="text"
                                                        className="form-control validate"
                                                        required
                                                    />
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <div className="col-xl-6 col-lg-6 col-md-12 mx-auto mb-4">
                                        <div className="tm-product-img-dummy mx-auto" style={ { maxWidth: '100%', height: '240px', display: 'flex', alignItems: 'center', justifyContent: 'center', color: '#fff', background: '#aaa' } }>
                                            <i className='fa fa-window-close'
                                                onClick={ this.removeTmpImg }
                                                id='closeIcon'
                                                style={ { position: 'absolute', top: '2%', right: '5%', fontSize: '1.5em',color: '#455c71', display: 'none' } }
                                            ></i>
                                            <label htmlFor="fileInput">
                                                <i
                                                    id="selectIcon"
                                                    className="fas fa-cloud-upload-alt tm-upload-icon"
                                                    style={ { background: '#455c71', width: '55px', height: '55px', borderRadius: '50%', textAlign: 'center', paddingTop: '15px', fontSize: '22px' } }
                                                ></i>

                                            </label>
                                            <img id="thumble" src=""
                                                style={ { maxWidth: '100%', height: '240px', display: 'none', alignItems: 'center', justifyContent: 'center', color: '#fff', background: '#aaa', } } />
                                        </div>
                                        <div className="custom-file mt-3 mb-3">
                                            <input id="fileInput" type="file" onChange={ e => this.imageChange(e) } style={ { display: 'none' } } />
                                            <input style={ { color: '#fff', backgroundColor: '#f5a623', border: '2px solid #f5a623', fontSize: '90%', fontWeight: '600' } }
                                                type="button"
                                                id="uploadBtn"
                                                className="btn btn-primary btn-block mx-auto"
                                                value="UPLOAD PRODUCT IMAGE"
                                                onClick={ this.uploadImage }
                                            />
                                        </div>
                                    </div>
                                    <div className="col-12" onClick={ this.handleSubmit }>
                                        { this.renderButton("ADD PRODUCT NOW", "btn btn-primary btn-block text-uppercase", " color: '#fff', backgroundColor: '#f5a623', border: '2px solid #f5a623', fontSize: '90%', fontWeight: '600' ") }
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