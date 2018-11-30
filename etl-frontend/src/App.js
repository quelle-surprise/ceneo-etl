import React, {Component} from 'react';
import MainMenu from './components/menu/MainMenu.js';
import {BrowserRouter as Router} from 'react-router-dom';
import createRoutes from './utils/routes.js'

class App extends Component {
    render() {
        return (
            <Router>
                <div>
                    <MainMenu/>
                    {createRoutes()}
                </div>
            </Router>
        );
    }
}

export default App;
