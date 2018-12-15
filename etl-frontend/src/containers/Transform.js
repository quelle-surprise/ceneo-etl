import React, {Component} from 'react';
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import PropTypes from "prop-types";
import {loadProduct, transformProduct} from "../actions/etl-actions";
import MaterialTable from "material-table";
import {Typography} from "@material-ui/core/index";
import {LinearProgress} from "@material-ui/core/es/index";
import {TextButton} from "../components/common/TextButton";
import {DATABASE_URL} from "../utils/routes";
import {PopUp} from "../components/common/PopUp";
import {Details} from "../components/home/Details";

const reviewsColumns = [
    {title: 'Review content', field: 'reviewContent'},
    {title: 'Name of reviewer', field: 'nameOfReviewer'},
    {title: 'Score', field: 'reviewScore'},
];

const LOAD_PRODUCT_ACTION_ID = 'load.product.action.id';

@connect(
    store => ({
        product: store.product.product,
        requestResult: store.api.requestResult,
        isFetching: store.api.isFetching
    }),
    dispatch => (bindActionCreators({transformProduct, loadProduct}, dispatch)))
class Transform extends Component {

    static propTypes = {
        product: PropTypes.object,
        transformProduct: PropTypes.func,
        loadProduct: PropTypes.func,
        isFetching: PropTypes.bool
    };

    state = {
        openPopUp: false
    };

    componentWillMount() {
        const {transformProduct} = this.props;
        transformProduct();
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.requestResult !== nextProps.requestResult) {
            if (nextProps.requestResult.success === true) {
                this.setState({
                    openPopUp: true
                });
            }
        }
    }

    render() {
        const {product, loadProduct} = this.props;

        return (
            product ?
                <React.Fragment>
                    {this.props.isFetching && <LinearProgress/>}
                    <Details>
                        <Typography variant="h6" paragraph>{product.productName}</Typography>
                        <Typography variant="subtitle2"
                                    paragraph>ID: {product.productId}</Typography>
                        <Typography variant="subtitle2"
                                    paragraph> Lowest price: {product.lowestPrice} zł</Typography>
                        <Typography variant="subtitle2" paragraph>Categories: {product.category}</Typography>
                        <TextButton onClick={() => loadProduct(LOAD_PRODUCT_ACTION_ID)}
                                    disabled={this.props.isFetching}>Load</TextButton>
                        <MaterialTable
                            columns={reviewsColumns}
                            data={product.reviews}
                            title="Reviews"
                            options={{
                                pageSize: 10
                            }}
                        />
                        {this.state.openPopUp && this.renderPopUp()}
                    </Details>
                </React.Fragment>
                : <LinearProgress/>
        );
    }

    renderPopUp() {
        const {product: {reviews}} = this.props;
        return (
            <PopUp
                openPopUp={this.state.openPopUp}
                title="SUCCESS"
                content={"Added 1 product with reviews count:  " + reviews.length}
                buttonLabel="Database"
                buttonRedirect={DATABASE_URL}
            />
        );
    }
}

export default Transform;
