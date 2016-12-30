import * as React from "react";
import { connect } from "react-redux"
import { Button, ButtonToolbar, ControlLabel, FormControl, Form, FormGroup, Checkbox, Col, Row, Panel } from "react-bootstrap"
import { Link } from "react-router"
import * as auth from "../../actions/auth"
import * as forms from "../../actions/forms"
import * as utilities from "../../utilities/random-coder"
import { store, AppState } from "../../Store"
import { FormState } from "../../reducers/forms"
import { Auth } from "../../reducers/auth"
import { RestErrorDto } from "../../utilities/http"
import { FormErrorPanel } from "../../components/FormErrorPanel"

type ReduxProps = {
    loginForm: FormState
    auth: Auth
}

type CommonProps = {
    params: any
}

type Props = ReduxProps & CommonProps;

type StaffLoginState = {
    userAuthDto: auth.UserAuthDto
    platform: string
}

let LOGIN_FORM_ID = utilities.makeRandomString(32);

export class StaffLoginPage extends React.Component<Props, StaffLoginState>{

    constructor(props: Props) {
        super(props);
        let platform = this.props.params['platform'];
        this.state = {
            userAuthDto: { username: "", password: "", userPlatformName: platform, userTypeName: "staff" },
            platform: platform
        }
        console.log("staffLogin:", platform);

        console.log("StaffLoginPage constructor");
    }

    componentWillMount() {
        let formState: FormState = { processing: false, restError: null }
        store.dispatch(forms.updateForm(LOGIN_FORM_ID, formState));
    }

    componentWillUnmount() {
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

    passwordKeyUp(event: KeyboardEvent) {
        if (event.keyCode == 13) {
            this.submit();
        }
    }

    submit() {
        if (this.props.loginForm.processing) {
            return;
        }
        store.dispatch(auth.triggerLogin(this.state.userAuthDto, LOGIN_FORM_ID));
    }

    render() {
        let loginForm = (
            <div>
                <FormErrorPanel formState={this.props.loginForm} />
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
                                    <FormControl name="password" type="password" placeholder="Password" onChange={this.fieldOnChange.bind(this)} onKeyUp={this.passwordKeyUp.bind(this)} />
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

        let redirectPanel = (
            <Panel header="current logged in">
                <Link to="admin/index">Click here to dashboard</Link>
            </Panel>
        );

        let panel = auth.isAdmin() ? redirectPanel : loginForm;
        return (
            <div className="container">
                <h1>Admin Login</h1>
                {panel}
            </div>
        );
    }
}

function select(state: AppState): ReduxProps {
    return { loginForm: state.forms[LOGIN_FORM_ID], auth: state.auth };
}

export let StaffLogin = connect(select)(StaffLoginPage);