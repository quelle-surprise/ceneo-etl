import React from 'react';
import styled from 'styled-components';
import Button from '@material-ui/core/Button';
import PropTypes from "prop-types";

const StyledButton = styled(Button)`
    && {
        border-radius: 3px;
        height: 48px;
        padding: 0 30px;
        margin: 5px;
    }
`;

export const TextButton = (props) => {
    const {color, ...rest} = props;

    return <StyledButton {...rest} color={color}/>
};

TextButton.propTypes = {
    label: PropTypes.string,
    color: PropTypes.string,
    variant: PropTypes.string,
    disabled: PropTypes.bool,
    onClick: PropTypes.func
};

TextButton.defaultProps = {
    color: 'primary',
    variant: 'contained'
};
