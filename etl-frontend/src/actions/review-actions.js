import axios from "axios";
import {API_ERROR, API_SUCCESS} from "./types";
import {DELETE_REVIEW_ENDPOINT} from "../config/data-properties";

export function deleteReview(productId, actionId) {
    return dispatch =>
        axios.delete(`${DELETE_REVIEW_ENDPOINT}/${productId}`)
            .then(dispatch({
                type: API_SUCCESS,
                actionId: actionId,
                payload: {
                    message: "Successfully deleted review",
                }
            }))
            .catch(dispatch({
                type: API_ERROR,
                actionId: actionId,
                payload: {
                    message: 'Unable to delete product review.'
                }
            }))
}