import * as React from "react";
import { connect } from "react-redux"
import { Button, ButtonToolbar, ControlLabel, FormControl, Form, FormGroup, Checkbox, Col, Row, Panel } from "react-bootstrap"
import { Link } from "react-router"
import * as me from "../../actions/me"
import * as forms from "../../actions/forms"
import * as zform from "../../components/zero/ZForm"
import * as utilities from "../../utilities/random-coder"
import { store, AppState } from "../../Store"
import { FormState } from "../../reducers/forms"
import { UserProfile } from "../../reducers/me"
import { RestErrorDto } from "../../utilities/http"
import { FormErrorPanel } from "../../components/FormErrorPanel"


interface Prop {
    me: UserProfile
}

interface AdminLoginState {
}


export class AdminLoginPage extends React.Component<Prop, AdminLoginState>{

    constructor() {
        super();
        console.log("AdminLoginPage constructor");
    }

    passwordKeyUp(event: KeyboardEvent) {
        if (event.keyCode == 13) {
            //this.submit();
        }
    }

    onSuccess() {
        store.dispatch(me.loadProfile());
    }

    render() {
        let loginForm = (
            <Row>
                <Col xs={12} sm={4} md={3}>
                    <zform.Form action="me/auth" method="PUT" onSuccess={this.onSuccess.bind(this)}>
                        <zform.Hidden name="userTypeName" value="admin" />
                        <zform.Hidden name="userPlatformName" value="admin" />
                        <zform.TextField name="username" label="Username" />
                        <zform.Password name="password" label="Password" />
                        <zform.Submit value="Login" />
                    </zform.Form>
                </Col>
            </Row>
        )
        let redirectPanel = (
            <Panel header="current logged in">
                <Link to="admin/index">Click here to dashboard</Link>
            </Panel>
        )
        let panel = me.isAdmin() ? redirectPanel : loginForm;
        return (
            <div className="container">
                <h1>Admin Login</h1>
                {panel}
            </div>
        )
    }
}

function select(state: AppState): Prop {
    console.log("AdminLogin select", state);
    return { me: state.me };
}

export let AdminLogin = connect(select)(AdminLoginPage);