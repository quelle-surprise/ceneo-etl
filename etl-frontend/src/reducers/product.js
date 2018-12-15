import {
    FETCH_PRODUCT_DETAILS_SUCCESS,
    FETCH_PRODUCTS_DETAILS_SUCCESS,
    TRANSFORM_PRODUCT_SUCCESS
} from "../actions/types";

export default function (state = {}, action) {
    switch (action.type) {
        case FETCH_PRODUCTS_DETAILS_SUCCESS:
            return {...state, product: action.payload};
        case TRANSFORM_PRODUCT_SUCCESS:
            return {...state, product: action.payload};
        case FETCH_PRODUCT_DETAILS_SUCCESS:
            return {...state, product: action.payload};
    }
    return state
}