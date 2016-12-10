import * as React from "react";
import * as ReactDOM from "react-dom";
import { Button, ButtonToolbar } from "react-bootstrap";

export class AdminLogin extends React.Component<{}, {}>{

    constructor() {
        super();
    }

    appInit() {
    }

    render() {
        return (
            <div className="container">
                <h1>Admin Login</h1>
                <ButtonToolbar>
                    <Button bsStyle="success" onClick={this.appInit.bind(this)}>OK</Button>
                </ButtonToolbar>
            </div>
        );
    }
}
