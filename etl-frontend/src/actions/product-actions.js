import axios from "axios";
import {
    API_ERROR,
    API_SUCCESS,
    FETCH_PRODUCT_DETAILS_ERROR,
    FETCH_PRODUCT_DETAILS_SUCCESS,
    FETCH_PRODUCTS_DETAILS_ERROR,
    FETCH_PRODUCTS_DETAILS_SUCCESS
} from "./types";
import {DELETE_PRODUCT_ENDPOINT, GET_PRODUCT_ENDPOINT, PRODUCTS_ENDPOINT} from "../config/data-properties";

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

export function fetchProduct(productId) {
    return dispatch =>
        axios.get(`${GET_PRODUCT_ENDPOINT}/${productId}`)
            .then(response => dispatch({
                type: FETCH_PRODUCT_DETAILS_SUCCESS,
                payload: response.data
            }))
            .catch(error => dispatch({
                type: FETCH_PRODUCT_DETAILS_ERROR
            }))
}

export function deleteProduct(productId, actionId) {
    return dispatch =>
        axios.delete(`${DELETE_PRODUCT_ENDPOINT}/${productId}`)
            .then(response => dispatch({
                type: API_SUCCESS,
                actionId: actionId,
                payload: {
                    message: "Successfully deleted product",
                }
            }))
            .catch(error => dispatch({
                type: API_ERROR,
                actionId: actionId,
                payload: {
                    message: 'Unable to delete product. Make sure that provided ID is correct and item is already in our database'
                }
            }))
}