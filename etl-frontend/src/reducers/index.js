import product from "./product";
import {combineReducers} from 'redux';
import api from "./api";

export default combineReducers({
    product,
    api
});