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

interface UserLoginState {
    message: string
}

export class UserLogin extends React.Component<{}, UserLoginState>{

    constructor() {
        super();
        this.state = { message: "" }
    }

    submitSuccessHandler(feedback: any) {
        let result = feedback["result"];
        switch (result) {
            case "SUCCESS":
                this.state.message = "welcome come back.";
                break;
            case "PASSWORD_WRONG":
                this.state.message = "username or password is wrong.";
                break;
            case "USER_LOGINED":
                this.state.message = "There is someone loged in. Please logout first."
                break;
            case "DATABASE_EXCEPTION":
                this.state.message = "sever is busy. Please try again later."
                break;
            default:
                this.state.message = "login failed";
                console.log(result);
        }
        this.setState(this.state);
    }

    render() {
        return (
            <div className="container">
                <h1>User Login</h1>
                <Row>
                    <Col sm={3}>
                        <p>{this.state.message}</p>
                        <ZForm.Form action="auth/login" submit="login" fields={fieldArray} onSuccess={this.submitSuccessHandler.bind(this)} />
                    </Col>
                </Row>
            </div>
        );
    }
}