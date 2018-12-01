import {API_SUCCESS} from "../actions/types";

export default function (state = {}, action) {
    switch (action.type) {
        case API_SUCCESS:
            return {...state, requestResult: {success: true, message: action.payload.message}};
    }
    return state
}
