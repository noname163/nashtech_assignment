import React, { Component } from "react";
import { Link } from "react-router-dom";
import Table from "./common/table";
import config from "../config.json";
import http from "../service/httpService";
import ProductModel from "./common/productModel";

class ProductTable extends Component {

    state={
        posts:[]
    };
    async componentDidMount() {
        const { data: posts } = await http.get(config.apiEndpoint + "/products");
        this.setState({ posts });
    }
    columns=[
        {path:"url",label:"Image"},
        {
            path: "name",
            label:"Product Name",
        },
        {path:"categories",label:"Categories"},
        {path:"price",label:"Price"},
        {path:"status",label:"Status"},
        {
            key: "like",
            label:"Action",
            content: data => (
                <div class="btn-group">
                    <button type="button" class="btn btn-primary">Edit</button>
                    <button type="button" class="btn btn-danger">Delete</button>
                </div>
            )
        }

    ]
    render() { 
        return (
        <React.Fragment>
            <Table
            columns={this.columns}
            data={this.state.posts}
          />
          <ProductModel/>
        </React.Fragment>
        );
    }
}
 
export default ProductTable;