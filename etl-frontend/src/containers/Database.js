import React, {Component} from 'react';
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {deleteProduct, fetchProducts} from "../actions/product-actions";
import PropTypes from "prop-types";
import MaterialTable from 'material-table';
import IconButton from "@material-ui/core/IconButton";
import RateReview from "@material-ui/icons/RateReview";
import {LinearProgress} from "@material-ui/core/index";

const productsColumns = [
    {title: 'ID', field: 'productId'},
    {title: 'Name', field: 'productName'},
    {title: 'Lowest Price (zł)', field: 'lowestPrice'},
    {title: 'Categories', field: 'category'},
    {
        title: 'Reviews',
        render: rowData => {
            return (
                <div>
                    <IconButton href={rowData.productId}>
                        <RateReview/>
                    </IconButton>
                    {rowData.reviews.length}
                </div>
            )
        }
    }
];

@connect(
    store => ({
        products: store.product.product,
        requestResult: store.api.requestResult
    }),
    dispatch => (bindActionCreators({fetchProducts, deleteProduct}, dispatch)))
class Database extends Component {

    static propTypes = {
        products: PropTypes.array,
        fetchProducts: PropTypes.func,
        deleteProduct: PropTypes.func
    };

    state = {
        fetched: false,
        firstFetchDone: false
    };

    static getDerivedStateFromProps(nextProps, prevState) {
        const {firstFetchDone} = prevState;

        if (firstFetchDone === false) {
            return {
                firstFetchDone: true,
                refresh: true
            };
        }
        return null
    }

    componentDidUpdate(prevProps, prevState) {
        if (this.props.requestResult !== prevProps.requestResult) {
            this.setState({
                refresh: true
            });
        }
    }

    render() {
        this.refreshTable();
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
                                rows.map((row) => this.props.deleteProduct(row.productId));
                            },
                        },
                    ]}
                    localization={{
                        actions: 'Reviews'
                    }}
                />
                : <LinearProgress/>
        );
    }

    refreshTable() {
        const {refresh} = this.state;
        const {fetchProducts} = this.props;

        if (refresh) {
            fetchProducts();
            this.setState({
                refresh: false
            })
        }
    }
}

export default Database;
