import * as React from "react";
import * as ReactDOM from "react-dom";
import { Button, ButtonToolbar, FormControl, FormGroup, ControlLabel, Col, Row } from "react-bootstrap"
import * as ZForm from "../../components/zero/ZForm"

let fieldArray = [
    { label: "Email", name: "email", type: "text", value: "" },
    { label: "Password", name: "password", type: "password", value: "" },
] as ZForm.Field[];

interface AdminLoginState {
    message: string
}

export class AdminLogin extends React.Component<{}, AdminLoginState>{

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
