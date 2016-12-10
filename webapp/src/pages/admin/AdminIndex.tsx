import * as React from "react";
import * as ReactDOM from "react-dom";
import { hashHistory } from "react-router"
import { Button, ButtonToolbar } from "react-bootstrap";

export class AdminIndex extends React.Component<{}, {}>{

    constructor() {
        super();
        let pathname = hashHistory.getCurrentLocation().pathname;
        console.log(pathname);
        // hashHistory.push('/')
    }

    appInit() {
    }

    render() {
        return (
            <div className="container">
                <h1>Admin Management System</h1>
                <ButtonToolbar>
                    <Button bsStyle="success" onClick={this.appInit.bind(this)}>OK</Button>
                </ButtonToolbar>
            </div>
        );
    }
}
