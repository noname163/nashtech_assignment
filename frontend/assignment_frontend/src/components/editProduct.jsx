import React, { Component, useState } from 'react';
import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import dataService from '../service/dataService.js';
import Select from 'react-select';
import { ToastContainer, toast } from 'react-toastify';
import productService from '../service/productService.js';

export default function EditProduct() {
    const location = useLocation();
    const data = location.state;
    const [product, setProduct] = useState({});

    const [cate, setCate] = useState([]);
    const [displayCloseIcon, setDisplayCloseIcon] = useState('');
    const [displayUploadIcon, setDisplayUploadIcon] = useState('none');
    const [displayUploadBtn, setDisplayUploadBtn] = useState('none');
    const [file, setFile] = useState();
    const [tmpImage, setTmpImage] = useState();
    const [displayThumble, setDisplayThumble] = useState('none');
    const [displayMainImage, setDisplayMainImage] = useState('');

    const getCate = async () => {
        const response = await axios.get("http://localhost:8080/products/categories");

        setCate(response.data.map(cate => {
            return {
                value: cate.name,
                label: cate.name
            }
        }));
    }
    useEffect(() => {
        getCate();

    }, []);
    useEffect(() => {
        setProduct(data);
    }, []);
    const handleChange = (e) => {
        setProduct({
            ...product,
            [e.target.name]: e.target.value,
        })
    };
    const removeTmpImg = () => {
        console.log("Onclick");
        setDisplayCloseIcon('none');
        setProduct({
            ...product,
            images: null
        });
        setDisplayUploadBtn('');
        setDisplayUploadIcon('');
        setDisplayMainImage('none');
        setDisplayThumble('none')
    }
    const imageChange = (e) => {
        setFile(e.target.files[0]);
        let tmpImage = URL.createObjectURL(e.target.files[0]);
        setTmpImage(tmpImage);
        setDisplayThumble('');
        setDisplayCloseIcon('');
        setDisplayUploadIcon('none');
        setDisplayMainImage('none');
    }
    const uploadImage = async () => {
        try {
            if (file != null) {
                let multipartFiles = file;
                let form = new FormData();
                form.append("multipartFiles", multipartFiles)
                let { data: url } = await axios.post("http://localhost:8080/image/images", form);
                let imagesUrls = [];
                imagesUrls.push(url.toString());
                setProduct({
                    ...product,
                    images: imagesUrls
                })
                setDisplayUploadBtn("none");
                toast.success('Upload Image Success.');
            }
            if (this.state.file == null) {
                toast.error("Empty Image.");
            }
        } catch (error) {
            console.log(error);
            toast.error(error);
        }


    }
    const submitProduct = async () => {
        try {
            console.log("Submiting");
            if (product.images == ""|| product.images == null) {
                toast.error("Submit Image First.")
            }
            if (product.images != "") {
                await productService.updateProduct(product);
                toast.success('Upload Product Success.');
                window.location="/product-admin";
            }
        } catch (ex) {
            console.log("Errors");
            console.log("Mess" + ex);
            if (ex.response && ex.response.status === 400) {
                console.log("errors");
                toast.error(ex.response.data.mess)
                console.log("Mess" + ex);
            }
        }
    }
    console.log("Image: ", product)
    console.log(cate);
    console.log("Category ", product.categories);
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
                                    <form action="" className="tm-edit-product-form" style={ { display: 'block', marginTop: '0em' } }>
                                        <div className="form-group mb-3" style={ { marginBottom: '1rem !important' } }>
                                            <label htmlFor="name">Product Name
                                            </label>
                                            <input
                                                id="name"
                                                name="name"
                                                type="text"
                                                value={ product?.name }
                                                className="form-control validate"
                                                onChange={ handleChange }
                                                required
                                            />

                                        </div>
                                        <div className="form-group mb-3" style={ { marginBottom: '1rem !important' } }>
                                            <label
                                                htmlFor="description">
                                                Description
                                            </label>
                                            <textarea
                                                className="form-control validate"
                                                rows="3"
                                                name='description'
                                                value={ product?.description }
                                                onChange={ handleChange }
                                                required
                                            ></textarea>
                                        </div>
                                        <div className="form-group mb-3" style={ { marginBottom: '1rem !important' } }>
                                            <Select defaultValue={ product.categories }
                                                selectedValue={ product.categories }
                                                defaultInputValue={ product.categories }
                                                placeholder={ product.categories }
                                                options={ cate } />
                                        </div>
                                        <div className="row" style={ { display: 'flex', flexWrap: 'wrap', marginRight: '-15px', marginLeft: -'15px' } }>
                                            <div className="form-group mb-3 col-xs-12 col-sm-6">
                                                <label
                                                    htmlFor="price">Price
                                                </label>
                                                <input
                                                    id="price"
                                                    name="price"
                                                    value={ product?.price }
                                                    onChange={ handleChange }
                                                    type="text"
                                                    className="form-control validate"
                                                    data-large-mode="true"
                                                />
                                            </div>
                                            <div className="form-group mb-3 col-xs-12 col-sm-6">
                                                <label
                                                    htmlFor="createdDate"
                                                >Created Date
                                                </label>
                                                <input
                                                    id="createdDate"
                                                    name="createdDate"
                                                    value={ product?.createdDate }
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
                                            onClick={ removeTmpImg }
                                            id='closeIcon'
                                            style={ { position: 'absolute', top: '2%', right: '5%', fontSize: '1.5em', color: '#455c71', display: `${ displayCloseIcon }` } }
                                        ></i>
                                        <label htmlFor="fileInput">
                                            <i
                                                id="selectIcon"
                                                className="fas fa-cloud-upload-alt tm-upload-icon"
                                                style={ { display: `${ displayUploadIcon }`, background: '#455c71', width: '55px', height: '55px', borderRadius: '50%', textAlign: 'center', paddingTop: '15px', fontSize: '22px' } }
                                            ></i>

                                        </label>
                                        <img id="image" src={ product.images != null ? product.images.slice(0, 1).map(url => url) : "" }
                                            style={ { maxWidth: '100%', height: '240px', display: `${ displayMainImage }`, alignItems: 'center', justifyContent: 'center', color: '#fff', background: '#aaa', } } />
                                        <img id="thumble" src={ tmpImage != null ? tmpImage : "" }
                                            style={ { maxWidth: '100%', height: '240px', display: `${ displayThumble }`, alignItems: 'center', justifyContent: 'center', color: '#fff', background: '#aaa', } } />
                                    </div>
                                    <div className="custom-file mt-3 mb-3">
                                        <input id="fileInput" type="file" onChange={ imageChange } style={ { display: 'none' } } />
                                        <input style={ { display: `${ displayUploadBtn }`, color: '#fff', backgroundColor: '#f5a623', border: '2px solid #f5a623', fontSize: '90%', fontWeight: '600' } }
                                            type="button"
                                            id="uploadBtn"
                                            onClick={ uploadImage }
                                            className="btn btn-primary btn-block mx-auto"
                                            value="UPLOAD PRODUCT IMAGE"
                                        />
                                    </div>
                                </div>
                                <div className="col-12" >
                                    <input style={ { color: '#fff', backgroundColor: '#f5a623', border: '2px solid #f5a623', fontSize: '90%', fontWeight: '600' } }
                                        type="button"
                                        id="uploadBtn"
                                        onClick={ submitProduct }
                                        className="btn btn-primary btn-block mx-auto"
                                        value="UPDATE PRODUCT INFORMATION"
                                    />
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </React.Fragment>
    )
}
