import React, {Component} from "react"
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import styled from 'styled-components';
import {LIGHT_GREY, WHITE} from '../../utils/colors-const';
import {DATABASE_URL, HOME_URL} from '../../utils/routes';

const Wrapper = styled.div`
    background-color: ${WHITE};
    margin-bottom: 20px;
    box-shadow: 0 4px 15px -2px ${LIGHT_GREY};
`;

export default class MainMenu extends Component {
    render() {
        return (
            <Wrapper>
                <Toolbar>
                    <Typography variant="h6" color="primary" gutterBottom>
                        ETL
                    </Typography>
                    <div style={{flexGrow: 1}}/>
                    <Button color="inherit" variant="link" href={HOME_URL}>Home</Button>
                    <Button color="inherit" variant="link" href={DATABASE_URL}>Database</Button>
                </Toolbar>
            </Wrapper>
        )
    }
}
