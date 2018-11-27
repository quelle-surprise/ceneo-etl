import { createMuiTheme } from '@material-ui/core/styles';
import { PRIMARY, SECONDARY, WHITE } from './colors-const';

export const mainTheme = createMuiTheme({
    palette: {
        primary: {
            main: PRIMARY,
            contrastText: WHITE
        },
        secondary: {
            main: SECONDARY,
            contrastText: WHITE
        }
    },
    shadows: ["none"]
});

export default mainTheme;