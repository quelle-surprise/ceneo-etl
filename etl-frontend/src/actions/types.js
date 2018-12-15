export const FETCH_PRODUCTS_DETAILS_SUCCESS = "FETCH_PRODUCTS_DETAILS_SUCCESS";
export const FETCH_PRODUCTS_DETAILS_ERROR = "FETCH_PRODUCTS_DETAILS_ERROR";
export const FETCH_PRODUCT_DETAILS_SUCCESS = "FETCH_PRODUCT_DETAILS_SUCCESS";
export const FETCH_PRODUCT_DETAILS_ERROR = "FETCH_PRODUCT_DETAILS_ERROR";
export const TRANSFORM_PRODUCT_SUCCESS = "TRANSFORM_PRODUCT_SUCCESS";
export const API_SUCCESS = "API_SUCCESS";
export const API_ERROR = "API_ERROR";
export const REQUEST_API = "REQUEST_API";

export const requestApi = () => ({
    type: REQUEST_API
});

export const successApi = (actionId, message, data) => ({
    type: API_SUCCESS,
    actionId: actionId,
    payload: {
        message: message,
        data: data
    }
});

export const errorApi = (actionId, message) => ({
    type: API_ERROR,
    actionId: actionId,
    payload: {
        message: message
    }
});