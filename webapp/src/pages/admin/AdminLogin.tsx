import * as React from "react";
import { connect } from "react-redux"
import { hashHistory } from "react-router"
import { Button, ButtonToolbar, ControlLabel, FormControl, Form, FormGroup, Checkbox, Col, Row, Panel } from "react-bootstrap"
import { Link } from "react-router"
import * as me from "../../actions/me"
import * as zform from "../../components/zero/ZForm"
import * as utilities from "../../utilities/random-coder"
import { store, AppState } from "../../Store"
import { UserProfile } from "../../reducers/me"
import { RestErrorDto } from "../../utilities/http"


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

    onSuccess() {
        store.dispatch(me.loadProfile());
    }

    logout() {
        store.dispatch(me.triggerLogout()).then(() => {
            hashHistory.replace("admin/login");
        })
    }

    render() {
        let loginForm = (
            <Row>
                <Col xs={12} sm={4} md={3}>
                    <zform.Form action="me/auth" method="PUT" onSuccess={this.onSuccess.bind(this)}>
                        <zform.Hidden name="userTypeName" value="admin" />
                        <zform.Hidden name="userPlatformName" value="admin" />
                        <zform.TextField name="username" label="Username" />
                        <zform.Password name="password" label="Password" enterSubmit={true} />
                        <zform.Submit value="Login" />
                    </zform.Form>
                </Col>
            </Row>
        )
        let onlineRedirectPanel = (
            <Panel header="current logged in">
                <Link to="admin/index">Click here to dashboard</Link>
            </Panel>
        )
        let wrongUserTypePanel = (
            <Panel header="current logged in">
                <p>You have logged in, but you are not admin</p>
                <a onClick={this.logout.bind(this)}>Click here to log out.</a>
            </Panel>
        )
        let panel = null;
        if (me.isLogged() == false) {
            panel = loginForm;
        } else if (me.isAdmin()) {
            panel = onlineRedirectPanel;
        } else {
            panel = wrongUserTypePanel
        }
        return (
            <div className="container">
                <h1>Admin Login</h1>
                {panel}
            </div>
        )
    }
}

function select(state: AppState): Prop {
    return { me: state.me };
}

export let AdminLogin = connect(select)(AdminLoginPage);