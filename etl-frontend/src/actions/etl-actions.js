import axios from "axios";
import {errorApi, requestApi, successApi, TRANSFORM_PRODUCT_SUCCESS} from "./types";
import {
    BACKEND_URL,
    EXTRACT_ENDPOINT,
    LOAD_ENDPOINT,
    PRODUCT_ENDPOINT,
    TRANSFORM_ENDPOINT
} from "../config/data-properties";

export function addProduct(productId, actionId) {
    return dispatch => {
        dispatch(requestApi());
        return axios.get(`${BACKEND_URL}${PRODUCT_ENDPOINT}/${productId}`)
            .then(response => dispatch(
                successApi(actionId, 'Successfully added product', response.data)))
            .catch(error => dispatch(
                errorApi(actionId, 'Unable to add product')))
    };
}

export function extractProduct(productId, actionId) {
    return dispatch => {
        dispatch(requestApi());
        return axios.get(`${BACKEND_URL}${EXTRACT_ENDPOINT}/${productId}`)
            .then(response => dispatch(
                successApi(actionId, 'Successfully extracted product')))
            .catch(error => dispatch(
                errorApi(actionId, 'Unable to extract product')))
    }

}

export function transformProduct(productId, actionId) {
    return dispatch =>
        axios.get(`${BACKEND_URL}${TRANSFORM_ENDPOINT}`)
            .then(response => dispatch({
                type: TRANSFORM_PRODUCT_SUCCESS,
                payload: response.data
            }))
            .catch(error => dispatch(
                errorApi(actionId, 'Unable to transform product')))
}

export function loadProduct(actionId) {
    return dispatch =>
        axios.get(`${BACKEND_URL}${LOAD_ENDPOINT}`)
            .then(response => dispatch(
                successApi(actionId, 'Successfully loaded added product')))
            .catch(error => dispatch(
                errorApi(actionId, 'Unable to load product')))
}