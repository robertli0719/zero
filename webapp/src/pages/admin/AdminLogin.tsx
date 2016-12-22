import * as React from "react";
import * as ReactDOM from "react-dom";
import { connect } from "react-redux"
import { Button, ButtonToolbar, ControlLabel, FormControl, Form, FormGroup, Checkbox, Col, Row } from "react-bootstrap"
import * as auth from "../../actions/auth"
import * as utilities from "../../utilities/random-coder"
import { store, AppState } from "../../Store"

interface AdminLoginState {
    userAuthDto: auth.UserAuthDto
    btnDisable: boolean
}

interface Prop {
    val: number
}

let LOGIN_FORM_ID = utilities.makeRandomString(32);

export class AdminLoginPage extends React.Component<Prop, AdminLoginState>{

    constructor() {
        super();
        this.state = {
            userAuthDto: { username: "", password: "", platform: "default", userType: "admin" },
            btnDisable: false
        }
    }

    fieldOnChange(event: React.FormEvent<HTMLInputElement>) {
        let name = event.currentTarget.name;
        let val = event.currentTarget.value;
        switch (name) {
            case "username":
                this.state.userAuthDto.username = val;
                break;
            case "password":
                this.state.userAuthDto.password = val;
                break;
        }
        this.setState(this.state);
    }

    submit() {
        if (this.state.btnDisable) {
            return;
        }
        this.state.btnDisable = true;
        this.setState(this.state);
        store.dispatch(auth.triggerLogin(this.state.userAuthDto, LOGIN_FORM_ID));
    }

    render() {
        console.log("adminLogin render");
        return (
            <div className="container">
                <h1>Admin Login</h1>
                <Row>
                    <Col xs={12} sm={6} md={4}>
                        <Form horizontal>
                            <FormGroup controlId="formHorizontalUsername">
                                <Col componentClass={ControlLabel} sm={3}>Username</Col>
                                <Col sm={9}>
                                    <FormControl name="username" type="text" placeholder="Username" onChange={this.fieldOnChange.bind(this)} />
                                </Col>
                            </FormGroup>

                            <FormGroup controlId="formHorizontalPassword">
                                <Col componentClass={ControlLabel} sm={3}>Password</Col>
                                <Col sm={9}>
                                    <FormControl name="password" type="password" placeholder="Password" onChange={this.fieldOnChange.bind(this)} />
                                </Col>
                            </FormGroup>

                            <FormGroup>
                                <Col smOffset={3} sm={9}>
                                    <a className="btn btn-default" disabled={this.state.btnDisable} onClick={this.submit.bind(this)}>Login</a>
                                </Col>
                            </FormGroup>
                        </Form>
                    </Col>
                </Row>
            </div>
        );
    }
}

function select(state: AppState): Prop {
    console.log("select in AdminLogin...", state)
    return { val: state.test.val };
}

export let AdminLogin = connect(select)(AdminLoginPage);