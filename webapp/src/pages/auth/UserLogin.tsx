import * as React from "react";
import * as ReactDOM from "react-dom";
import { Button, ButtonToolbar, FormControl, FormGroup, ControlLabel, Col, Row } from "react-bootstrap"
import { connect } from "react-redux"
import { AppState } from "../../Store"
import * as ZForm from "../../components/zero/ZForm"

let fieldArray = [
    { label: "Email", name: "email", type: "text", value: "" },
    { label: "Password", name: "password", type: "password", value: "" },
] as ZForm.Field[];

export class UserLogin extends React.Component<{}, {}>{

    render() {
        return (
            <div className="container">
                <h1>User Login</h1>
                <Row>
                    <Col sm={3}>
                        <ZForm.Form action="auth/login" submit="login" fields={fieldArray} />
                    </Col>
                </Row>
            </div>
        );
    }
}