import axios from "axios";
import {
    errorApi,
    FETCH_PRODUCT_DETAILS_ERROR,
    FETCH_PRODUCT_DETAILS_SUCCESS,
    FETCH_PRODUCTS_DETAILS_ERROR,
    FETCH_PRODUCTS_DETAILS_SUCCESS,
    successApi
} from "./types";
import {BACKEND_URL, DELETE_PRODUCT_ENDPOINT, GET_PRODUCT_ENDPOINT, PRODUCTS_ENDPOINT} from "../config/data-properties";

export function fetchProducts() {
    return dispatch =>
        axios.get(`${BACKEND_URL}${PRODUCTS_ENDPOINT}`)
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
        axios.get(`${BACKEND_URL}${GET_PRODUCT_ENDPOINT}/${productId}`)
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
        axios.delete(`${BACKEND_URL}${DELETE_PRODUCT_ENDPOINT}/${productId}`)
            .then(response => dispatch(
                successApi(actionId, 'Successfully deleted product')
            ))
            .catch(error => dispatch(
                errorApi(actionId, 'Unable to delete product.')))
}