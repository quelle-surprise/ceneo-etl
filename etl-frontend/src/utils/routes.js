import React, { Component } from 'react';
import {Route, Switch} from 'react-router-dom';
import Home from '../containers/Home.js';
import Database from '../containers/Database.js';

export const HOME_URL = '/';
export const DATABASE_URL = '/database';

export default function createRoutes() {
    return (
        <Switch>
            <Route exact path={HOME_URL} component={Home} />
            <Route exact path={DATABASE_URL} component={Database} />
        </Switch>
    )
}