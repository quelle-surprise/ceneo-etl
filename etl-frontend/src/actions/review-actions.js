import axios from "axios";
import {errorApi, successApi} from "./types";
import {BACKEND_URL, DELETE_REVIEW_ENDPOINT} from "../config/data-properties";

export function deleteReview(productId, actionId) {
    return dispatch =>
        axios.delete(`${BACKEND_URL}${DELETE_REVIEW_ENDPOINT}/${productId}`)
            .then(dispatch(
                successApi(actionId, 'Successfully deleted review')))
            .catch(dispatch(
                errorApi(actionId, 'Unable to delete product review')))
}