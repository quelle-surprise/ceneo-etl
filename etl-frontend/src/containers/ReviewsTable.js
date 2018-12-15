import React from 'react';
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {fetchProduct} from "../actions/product-actions";
import PropTypes from "prop-types";
import MaterialTable from 'material-table';
import {LinearProgress} from "@material-ui/core/index";
import {deleteReview} from "../actions/review-actions";
import {Details} from "../components/home/Details";
import Typography from "@material-ui/core/Typography/Typography";

const DELETE_REVIEW_ACTION_ID = 'delete.review.action.id';

const reviewsColumns = [
    {title: 'ID', field: 'id'},
    {title: 'Content', field: 'reviewContent'},
    {title: 'Username', field: 'nameOfReviewer'},
    {title: 'Score', field: 'reviewScore'},
];

@connect(
    store => ({
        product: store.product.product,
        requestResult: store.api.requestResult
    }),
    dispatch => (bindActionCreators({fetchProduct, deleteReview}, dispatch)))
class ReviewsTable extends React.PureComponent {

    static propTypes = {
        product: PropTypes.array,
        fetchProduct: PropTypes.func,
        deleteReview: PropTypes.func,
        requestResult: PropTypes.object
    };

    state = {
        productId: null,
        refresh: false,
        rows: null,
    };

    static getDerivedStateFromProps(nextProps, prevState) {
        const {match: {params: {id}}} = nextProps;
        const {productId} = prevState;

        if (productId !== id) {
            return {
                productId: id,
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
        const {product} = this.props;
        this.refreshTable();

        return (
            product ? this.renderReviewsTable() : <LinearProgress/>
        );

    }

    renderReviewsTable() {
        this.refreshTable();
        const {product} = this.props;

        return (
            <Details>
                {this.refreshTable()}
                <Typography variant="h6" paragraph> Reviews for product with ID: {product.productId}</Typography>
                <MaterialTable
                    columns={reviewsColumns}
                    data={product.reviews}
                    title="Reviews"
                    options={{
                        columnsButton: true,
                        exportButton: true,
                        selection: true,
                        filtering: true,
                        pageSize: 8,
                    }}
                    actions={[
                        {
                            icon: 'delete_forever',
                            tooltip: 'Delete selected',
                            onClick: (event, rows) => {
                                rows.forEach(row => {
                                    this.props.deleteReview(row.id, DELETE_REVIEW_ACTION_ID);
                                });
                            }
                        },

                    ]}
                    localization={{
                        actions: 'Reviews'
                    }}
                />
                {this.refreshTable()}
            </Details>

        )

    }

    refreshTable() {
        const {refresh, productId} = this.state;
        const {fetchProduct} = this.props;

        if (refresh) {
            fetchProduct(productId);
            this.setState({
                refresh: false
            })
        }
    }

}

export default ReviewsTable;
