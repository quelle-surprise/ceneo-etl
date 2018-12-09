import React, {Component} from 'react';
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import PropTypes from "prop-types";
import {loadProduct, transformProduct} from "../actions/product-actions";
import MaterialTable from "material-table";
import {Paper, Typography} from "@material-ui/core/index";
import {LinearProgress} from "@material-ui/core/es/index";
import {TextButton} from "../components/common/TextButton";
import styled from "@material-ui/styles/es/styled";
import {DATABASE_URL} from "../utils/routes";
import {PopUp} from "../components/common/PopUp";

const reviewsColumns = [
    {title: 'Review content', field: 'reviewContent'},
    {title: 'Name of reviewer', field: 'nameOfReviewer'},
    {title: 'Score', field: 'reviewScore'},
];

const ProductDetails = styled(Paper)({
    textAlign: 'center',
    paddingTop: '30px'
});

const LOAD_PRODUCT_ACTION_ID = 'load.product.action.id';

@connect(
    store => ({product: store.product.product, requestResult: store.api.requestResult}),
    dispatch => (bindActionCreators({transformProduct, loadProduct}, dispatch)))
class Transform extends Component {

    static propTypes = {
        product: PropTypes.object,
        transformProduct: PropTypes.func,
        loadProduct: PropTypes.func
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
                <ProductDetails>
                    <Typography variant="h6" paragraph>{product.productName}</Typography>
                    <Typography variant="subtitle2"
                                paragraph>{product.productId} | {product.lowestPrice} zł</Typography>
                    <Typography variant="subtitle2" paragraph>{product.category}</Typography>
                    <TextButton onClick={() => loadProduct(LOAD_PRODUCT_ACTION_ID)}>Load</TextButton>
                    <MaterialTable
                        columns={reviewsColumns}
                        data={product.reviews}
                        title="Reviews"
                        options={{
                            pageSize: 8
                        }}
                    />
                    {this.state.openPopUp && this.renderPopUp()}
                </ProductDetails>
                : <LinearProgress/>
        );
    }

    renderPopUp() {
        const {product: {reviews}} = this.props;
        return (
            <PopUp
                openPopUp={this.state.openPopUp}
                title="SUCCESS 🎉"
                content={"Added 1 product with " + reviews.length + " reviews"}
                buttonLabel="Database"
                buttonRedirect={DATABASE_URL}
            />
        );
    }
}

export default Transform;
