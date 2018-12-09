import React, {Component} from 'react';
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {fetchProducts, deleteProduct} from "../actions/product-actions";
import PropTypes from "prop-types";
import MaterialTable from 'material-table';
import IconButton from "@material-ui/core/IconButton";
import RateReview from "@material-ui/icons/RateReview";
import {LinearProgress} from "@material-ui/core/index";
import {REVIEWS_URL} from "../utils/routes";

const productsColumns = [
    {title: 'ID', field: 'productId'},
    {title: 'Name', field: 'productName'},
    {title: 'Lowest Price (zł)', field: 'lowestPrice', type: 'numeric'},
    {title: 'Categories', field: 'category'},
    {
        title: 'Reviews',
        field: 'successScore',
        render: rowData => {
            return (
                <IconButton href={rowData.productId}>
                    <RateReview/>
                </IconButton>
            )
        }
    }
];

@connect(
    store => ({products: store.product.product}),
    dispatch => (bindActionCreators({fetchProducts, deleteProduct}, dispatch)))
class Database extends Component {

    static propTypes = {
        products: PropTypes.array,
        fetchProducts: PropTypes.func,
        deleteProduct: PropTypes.func
    };

    componentWillMount() {
        const {fetchProducts} = this.props;
        fetchProducts();
    }

    render() {
        const {products} = this.props;

        return (
            products ?
                <MaterialTable
                    columns={productsColumns}
                    data={products}
                    title="Products"
                    options={{
                        columnsButton: true,
                        exportButton: true,
                        selection: true,
                        filtering: true,
                        pageSize: 8
                    }}
                    actions={[
                        {
                            icon: 'delete_forever',
                            tooltip: 'Delete selected',
                            onClick: (event, rows) => {
                                rows.map((row) => this.props.deleteProduct(row.productId))
                                window.location.reload();
                            },
                        },
                    ]}
                    localization={{
                        actions: 'Reviews'
                    }}
                /> : <LinearProgress/>
        );
    }
}

export default Database;
