import React from 'react';
import {Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle} from "@material-ui/core/es/index";
import PropTypes from "prop-types";
import {TextButton} from "./TextButton";

export const PopUp = (props) => {
    const {content, title, buttonLabel, buttonRedirect, openPopUp, onPopUpClose} = props;

    return (
        <Dialog open={openPopUp}
                onClose={onPopUpClose}>
            <DialogTitle>{title}</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    {content}
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                {buttonRedirect &&
                <TextButton href={buttonRedirect}>
                    {buttonLabel}
                </TextButton>}
            </DialogActions>
        </Dialog>
    )
};


PopUp.propTypes = {
    openErrorPopUp: PropTypes.func,
    onPopUpClose: PropTypes.func,
    title: PropTypes.string,
    content: PropTypes.string,
    buttonType: PropTypes.string,
    buttonRedirect: PropTypes.string,
    buttonLabel: PropTypes.string
};