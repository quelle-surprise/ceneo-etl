import {API_ERROR, API_SUCCESS, REQUEST_API} from "../actions/types";

export default function (state = {}, action) {
    switch (action.type) {
        case REQUEST_API:
            return {
                ...state,
                isFetching: true
            };
        case API_SUCCESS:
            return {
                ...state,
                isFetching: false,
                requestResult: {
                    id: action.actionId,
                    success: true,
                    message: action.payload.message,
                    data: action.payload.data
                }
            };
        case API_ERROR:
            return {
                ...state,
                isFetching: false,
                requestResult: {
                    id: action.actionId,
                    success: false,
                    message: action.payload.message
                }
            };
    }
    return state
}
