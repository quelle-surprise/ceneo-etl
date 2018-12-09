import {API_ERROR, API_SUCCESS} from "../actions/types";

export default function (state = {}, action) {
    switch (action.type) {
        case API_SUCCESS:
            return {...state, requestResult: {id: action.actionId, success: true, message: action.payload.message, data: action.payload.data}};
        case API_ERROR:
            return {...state, requestResult: {id: action.actionId, success: false, message: action.payload.message}};
    }
    return state
}
