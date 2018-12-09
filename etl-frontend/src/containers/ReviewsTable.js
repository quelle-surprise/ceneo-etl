import React, {Component} from 'react';
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {fetchProduct} from "../actions/product-actions";
import PropTypes from "prop-types";
import MaterialTable from 'material-table';
import IconButton from "@material-ui/core/IconButton";
import RateReview from "@material-ui/icons/RateReview";
import {LinearProgress} from "@material-ui/core/index";
import {Route, Switch} from 'react-router-dom';
import {REVIEWS_URL} from "../utils/routes";

const reviewsColumns = [
    {title: 'ID', field: 'id'},
    {title: 'Content', field: 'reviewContent'},
    {title: 'Username', field: 'nameOfReviewer'},
    {title: 'Score', field: 'reviewScore'},
];


@connect(
    store => ({product: store.product.product}),
    dispatch => (bindActionCreators({fetchProduct}, dispatch)))

class ReviewsTable extends Component {

    static propTypes = {
        product: PropTypes.array,
        fetchProduct: PropTypes.func,
    };

    componentWillMount() {
        const {fetchProduct} = this.props;
        fetchProduct(this.props.match.params.id);
        console.log("cokolwiek");
    }

    render() {
        const {product} = this.props;
        return (
            product ? this.renderReviewsTable() : <LinearProgress/>
        );
    }

    renderReviewsTable() {
        const {product: {reviews}} = this.props;
        console.log(reviews);
        return (
            <MaterialTable
                                columns={reviewsColumns}
                                data={reviews}
                                title="Reviews"
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
//                                            rows.map((row) => this.props.deleteProduct(row.productId))
//                                            window.location.reload();
                                        },
                                    },
                                ]}
                                localization={{
                                    actions: 'Reviews'
                                }}
                            />
        );
    }
}

export default ReviewsTable;
