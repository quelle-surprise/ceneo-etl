import React, {Component} from 'react';
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {addProduct, extractProduct} from "../actions/etl-actions";
import PropTypes from "prop-types";
import {FormControl, TextField} from "@material-ui/core/index";
import {TextButton} from "../components/common/TextButton";
import Wrapper from "../components/home/Wrapper";
import Form from "../components/home/Form";
import Image from "../components/home/Image";
import {PopUp} from "../components/common/PopUp";
import {TRANSFORM_URL} from "../utils/routes";
import LinearProgress from "@material-ui/core/LinearProgress/LinearProgress";

const ADD_PRODUCT_ACTION_ID = 'add.product.action.id';
const EXTRACT_PRODUCT_ACTION_ID = 'extract.product.action.id';

@connect(
    store => ({
        requestResult: store.api.requestResult,
        isFetching: store.api.isFetching
    }),
    dispatch => (bindActionCreators({addProduct, extractProduct}, dispatch)))
class Home extends Component {

    static propTypes = {
        requestResult: PropTypes.object,
        products: PropTypes.array,
        addProduct: PropTypes.func,
        extractProduct: PropTypes.func,
        isFetching: PropTypes.bool
    };

    componentWillReceiveProps(nextProps) {
        if (this.props.requestResult !== nextProps.requestResult) {
            if (nextProps.requestResult.success === false) {
                this.setState({
                    openErrorPopUp: true
                });
            } else {
                this.setState({
                    openSuccessPopUp: true
                });
            }
        }
    }

    state = {
        productId: '',
        openErrorPopUp: false,
        openSuccessPopUp: false
    };

    render() {
        const {productId} = this.state;
        const {addProduct, extractProduct, isFetching} = this.props;

        return (
            <React.Fragment>
                {isFetching && <LinearProgress/>}
                <Wrapper>
                    <Form>
                        <Image src="../../static/assets/Home.jpg"/>
                        <FormControl>
                            <TextField
                                value={productId}
                                name="productId"
                                label="Product ID"
                                margin="normal"
                                onChange={this.handleFieldChange.bind(this)}
                            />
                            <div>
                                <TextButton
                                    onClick={() => addProduct(productId, ADD_PRODUCT_ACTION_ID)}
                                    disabled={isFetching}>ETL</TextButton>
                                <TextButton onClick={() => extractProduct(productId, EXTRACT_PRODUCT_ACTION_ID)}
                                            disabled={isFetching}
                                            color="secondary">Extract</TextButton>
                            </div>
                        </FormControl>
                    </Form>
                    {this.state.openErrorPopUp && this.renderErrorPopUp()}
                    {this.state.openSuccessPopUp && this.renderSuccessPopUp()}
                </Wrapper>
            </React.Fragment>
        );
    }

    handleFieldChange(e) {
        this.setState({
                productId: e.target.value
            }
        )
    }

    renderErrorPopUp() {
        return (
            <PopUp
                openPopUp={this.state.openErrorPopUp}
                onPopUpClose={this.handlePopUpClose}
                title="ERROR️"
                content={"Unable to get product. Make sure that provided ID " +
                "is correct and item is not already in our database "}
            />
        )
    }

    renderSuccessPopUp() {
        const {requestResult} = this.props;

        switch (requestResult.id) {
            case ADD_PRODUCT_ACTION_ID:
                return (
                    <PopUp
                        openPopUp={this.state.openSuccessPopUp}
                        onPopUpClose={this.handlePopUpClose}
                        title="SUCCESS"
                        content={"Added 1 product with reviews count: " + requestResult.data.reviews.length}
                    />
                );
            case EXTRACT_PRODUCT_ACTION_ID:
                return (
                    <PopUp
                        openPopUp={this.state.openSuccessPopUp}
                        title="SUCCESS"
                        content={"Extracted 1 product. You can now transform " +
                        "acquired html to object with product details."}
                        buttonLabel="Transform"
                        buttonRedirect={TRANSFORM_URL}
                    />
                );
        }
    }

    handlePopUpClose = () => {
        this.setState({
            openErrorPopUp: false,
            openSuccessPopUp: false
        });
    };
}

export default Home;
