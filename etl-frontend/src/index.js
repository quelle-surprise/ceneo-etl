import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import {MuiThemeProvider} from '@material-ui/core/styles';
import {mainTheme as theme} from './utils/themes';
import {applyMiddleware, createStore} from 'redux';
import thunk from 'redux-thunk';
import reducer from './reducers';
import {Provider} from "react-redux";

const store = createStore(
    reducer,
    applyMiddleware(thunk)
);

ReactDOM.render(
    <MuiThemeProvider theme={theme}>
        <Provider store={store}>
            <App/>
        </Provider>
    </MuiThemeProvider>
    , document.getElementById('root'));
serviceWorker.unregister();
