import * as React from "react";
import * as ReactDOM from "react-dom";
import { Button, ButtonToolbar, FormControl, FormGroup, ControlLabel, Col, Row } from "react-bootstrap"
import { connect } from "react-redux"
import { AppState } from "../../Store"
import * as ZForm from "../../components/zero/ZForm"

let fieldArray = [
    { label: "Email", name: "email", type: "text", value: "" },
    { label: "Password", name: "password", type: "password", value: "" },
    { label: "Password Again", name: "passwordAgain", type: "password", value: "" },
    { label: "Nickname", name: "name", type: "text", value: "" }
] as ZForm.Field[];

interface UserRegisterState {
    submitSuccess: boolean
}

export class UserRegister extends React.Component<{}, UserRegisterState>{

    constructor() {
        super();
        this.state = { submitSuccess: false }
    }

    submitSuccessHandler(feedback: any) {
        this.state.submitSuccess = true;
        this.setState(this.state);
    }

    render() {
        if (this.state.submitSuccess) {
            return (
                <div className="container">
                    <h1>Your register has been submitted successfully.</h1>
                    <p>Please check your email to verify it.</p>
                </div>
            );
        }
        return (
            <div className="container">
                <h1>User Register</h1>
                <Row>
                    <Col sm={3}>
                        <ZForm.Form action="auth/register" submit="register" fields={fieldArray} onSuccess={this.submitSuccessHandler.bind(this)} />
                    </Col>
                </Row>
            </div>
        );
    }
}