import axios from "axios";
import {
    API_ERROR,
    API_SUCCESS,
    FETCH_PRODUCTS_DETAILS_ERROR,
    FETCH_PRODUCTS_DETAILS_SUCCESS,
    TRANSFORM_PRODUCT_SUCCESS
} from "./types";
import {
    EXTRACT_ENDPOINT,
    LOAD_ENDPOINT,
    PRODUCT_ENDPOINT,
    PRODUCTS_ENDPOINT,
    TRANSFORM_ENDPOINT
} from "../config/data-properties";

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

export function addProduct(productId, actionId) {
    return dispatch =>
        axios.get(`${PRODUCT_ENDPOINT}/${productId}`)
            .then(response => dispatch({
                type: API_SUCCESS,
                actionId: actionId,
                payload: {
                    message: "Successfully added product",
                    data: response.data
                }
            }))
            .catch(error => dispatch({
                type: API_ERROR,
                actionId: actionId,
                payload: {
                    message: 'Unable to add product. Make sure that provided ID is correct and item is not already in our database'
                }
            }))
}

export function extractProduct(productId, actionId) {
    return dispatch =>
        axios.get(`${EXTRACT_ENDPOINT}/${productId}`)
            .then(response => dispatch({
                type: API_SUCCESS,
                actionId: actionId,
                payload: {
                    message: 'Successfully extracted product'
                }
            }))
            .catch(error => dispatch({
                type: API_ERROR,
                actionId: actionId,
                payload: {
                    message: 'Unable to extract product. Make sure that provided ID is correct and item is not already in our database'
                }
            }))
}

export function transformProduct(productId, actionId) {
    return dispatch =>
        axios.get(`${TRANSFORM_ENDPOINT}`)
            .then(response => dispatch({
                type: TRANSFORM_PRODUCT_SUCCESS,
                payload: response.data
            }))
            .catch(error => dispatch({
                type: API_ERROR,
                actionId: actionId,
                payload: {
                    message: 'Unable to transform product'
                }
            }))
}

export function loadProduct(actionId) {
    return dispatch =>
        axios.get(`${LOAD_ENDPOINT}`)
            .then(response => dispatch({
                type: API_SUCCESS,
                actionId: actionId,
                payload: {
                    message: 'Successfully loaded product'
                }
            }))
            .catch(error => dispatch({
                type: API_ERROR,
                actionId: actionId,
                payload: {
                    message: 'Unable to load product'
                }
            }))
}