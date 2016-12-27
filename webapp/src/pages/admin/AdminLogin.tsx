import * as React from "react";
import * as ReactDOM from "react-dom";
import { connect } from "react-redux"
import { Button, ButtonToolbar, ControlLabel, FormControl, Form, FormGroup, Checkbox, Col, Row } from "react-bootstrap"
import * as auth from "../../actions/auth"
import * as forms from "../../actions/forms"
import * as utilities from "../../utilities/random-coder"
import { store, AppState } from "../../Store"
import { FormState } from "../../reducers/forms"
import { Auth } from "../../reducers/auth"
import { RestErrorDto } from "../../utilities/http"

interface AdminLoginState {
    userAuthDto: auth.UserAuthDto
}

interface Prop {
    loginForm: FormState
    auth: Auth
}

let LOGIN_FORM_ID = utilities.makeRandomString(32);

export class AdminLoginPage extends React.Component<Prop, AdminLoginState>{

    constructor() {
        super();
        console.log("constructor");
    }

    componentWillMount() {
        console.log("componentWillMount");
        this.state = {
            userAuthDto: { username: "", password: "", platform: "default", userType: "admin" },
        }
        let formState: FormState = { processing: false, restError: null }
        store.dispatch(forms.updateForm(LOGIN_FORM_ID, formState));
    }

    componentWillUnmount() {
        console.log("componentWillUnmount");
        store.dispatch(forms.deleteForm(LOGIN_FORM_ID));
    }

    isDisabledUI() {
        return this.props.loginForm && this.props.loginForm.processing;
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
        if (this.props.loginForm.processing) {
            return;
        }
        store.dispatch(auth.triggerLogin(this.state.userAuthDto, LOGIN_FORM_ID))
            .then(() => {
                console.log("after login:", this.props.auth);
            })
            .catch((error: RestErrorDto) => {
                console.log("after login catch:", error);
            });
    }

    render() {
        console.log("adminLogin render");
        let errorMessage = <div></div>
        if (this.props.loginForm && this.props.loginForm.restError) {
            errorMessage = <p>{this.props.loginForm.restError.status}</p>
            //we need a error shower Component here....
        }
        return (
            <div className="container">
                <h1>Admin Login</h1>
                {errorMessage}
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
                                    <a className="btn btn-default" disabled={this.isDisabledUI()} onClick={this.submit.bind(this)}>Login</a>
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
    return { loginForm: state.forms[LOGIN_FORM_ID], auth: state.auth };
}

export let AdminLogin = connect(select)(AdminLoginPage);