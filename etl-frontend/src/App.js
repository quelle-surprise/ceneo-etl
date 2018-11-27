import React, { Component } from 'react';
import { TextButton as Button } from './components/common/TextButton.js';
import MainMenu from './components/menu/MainMenu.js';
import { BrowserRouter as Router} from 'react-router-dom';
import Home from './containers/Home.js';
import Database from './containers/Database.js';
import {HOME_URL, DATABASE_URL} from './utils/routes.js'
import createRoutes from './utils/routes.js';

class App extends Component {
  render() {
    return (
        <Router>
          <div>
            <MainMenu />
            {createRoutes()}
          </div>
        </Router>
    );
  }
}

export default App;
