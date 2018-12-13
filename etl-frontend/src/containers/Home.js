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

const ADD_PRODUCT_ACTION_ID = 'add.product.action.id';
const EXTRACT_PRODUCT_ACTION_ID = 'extract.product.action.id';

@connect(
    store => ({requestResult: store.api.requestResult}),
    dispatch => (bindActionCreators({addProduct, extractProduct}, dispatch)))
class Home extends Component {

    static propTypes = {
        requestResult: PropTypes.object,
        products: PropTypes.array,
        addProduct: PropTypes.func,
        extractProduct: PropTypes.func,
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
        const {addProduct, extractProduct} = this.props;

        return (
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
                                onClick={() => addProduct(productId, ADD_PRODUCT_ACTION_ID)}>ETL</TextButton>
                            <TextButton onClick={() => extractProduct(productId, EXTRACT_PRODUCT_ACTION_ID)}
                                        color="secondary">Extract</TextButton>
                        </div>
                    </FormControl>
                </Form>
                {this.state.openErrorPopUp && this.renderErrorPopUp()}
                {this.state.openSuccessPopUp && this.renderSuccessPopUp()}
            </Wrapper>
        );
    }

    handleFieldChange(e) {
        this.setState({
                productId: e.target.value
            }
        )
    }

    renderErrorPopUp() {
        const {requestResult} = this.props;
        return (
            <PopUp
                openPopUp={this.state.openErrorPopUp}
                onPopUpClose={this.handlePopUpClose}
                title="ERROR ☹️"
                content={requestResult.message}
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
                        title="SUCCESS 🥳"
                        content={"Added 1 item with " + requestResult.data.reviews.length + " reviews"}
                    />
                );
            case EXTRACT_PRODUCT_ACTION_ID:
                return (
                    <PopUp
                        openPopUp={this.state.openSuccessPopUp}
                        title="SUCCESS 🥳"
                        content={requestResult.message}
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
