import axios from "axios";
import {FETCH_PRODUCTS_DETAILS_ERROR, FETCH_PRODUCTS_DETAILS_SUCCESS} from "./types";
import {PRODUCTS_ENDPOINT} from "../config/data-properties";

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
};
