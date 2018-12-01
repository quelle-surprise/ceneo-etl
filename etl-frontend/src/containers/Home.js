import React, {Component} from 'react';
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {addProduct} from "../actions/product-actions";
import PropTypes from "prop-types";
import {FormControl} from "@material-ui/core/index";
import {TextButton} from "../components/common/TextButton";
import {TextField} from "@material-ui/core/es/index";
import Wrapper from "../components/home/Wrapper";
import Form from "../components/home/Form";
import Image from "../components/home/Image";

@connect(
    store => ({api: store.api}),
    dispatch => (bindActionCreators({addProduct}, dispatch)))
class Home extends Component {

    static propTypes = {
        products: PropTypes.array,
        addProduct: PropTypes.func
    };

    state = {
        productId: ""
    };

    render() {

        return (
            <Wrapper>
                <Form>
                    <Image src="../../static/assets/Home.jpg"/>
                    <FormControl>
                        <TextField
                            value={this.state.productId}
                            name="productId"
                            label="Product ID"
                            margin="normal"
                            onChange={this.handleFieldChange.bind(this)}
                        />
                        <div>
                            <TextButton onClick={this.onEtlButtonClick.bind(this)}>ETL</TextButton>
                            <TextButton color="secondary">Extract</TextButton>
                        </div>
                    </FormControl>
                </Form>
            </Wrapper>
        );
    }

    onEtlButtonClick() {
        const {productId} = this.state;
        const {addProduct} = this.props;
        addProduct(productId);
    }

    handleFieldChange(e) {
        this.setState({
                productId: e.target.value
            }
        )
    }
}

export default Home;
