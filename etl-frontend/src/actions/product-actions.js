import axios from "axios";
import {API_ERROR, API_SUCCESS, FETCH_PRODUCTS_DETAILS_ERROR, FETCH_PRODUCTS_DETAILS_SUCCESS} from "./types";
import {PRODUCT_ENDPOINT, PRODUCTS_ENDPOINT} from "../config/data-properties";

export function fetchProducts() {
    return dispatch =>
        axios.get(`${PRODUCTS_ENDPOINT}`)
            .then(response => dispatch({
                type: FETCH_PRODUCTS_DETAILS_SUCCESS,
                payload: response.data
            }))
            .catch(error => dispatch({
                type: FETCH_PRODUCTS_DETAILS_ERROR
            }))
}

export function addProduct(productId) {
    return dispatch =>
        axios.post(`${PRODUCT_ENDPOINT}/${productId}`)
            .then(response => dispatch({
                type: API_SUCCESS,
                payload: {
                    message: message
                }
            }))
            .catch(error => dispatch({
                type: API_ERROR
            }))
}
