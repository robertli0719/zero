import * as React from "react"
import { connect } from "react-redux"
import { hashHistory, Link } from "react-router"
import { Button, ButtonToolbar, ControlLabel, FormControl, Form, FormGroup, Checkbox, Col, Row, Panel } from "react-bootstrap"
import * as me from "../../actions/me"
import * as zform from "../../components/zero/zform/zform"
import * as utilities from "../../utilities/random-coder"
import { store, AppState } from "../../Store"
import { UserProfile } from "../../reducers/me"
import { RestErrorDto } from "../../utilities/http"


interface Prop {
    me: UserProfile
}

interface LoginState {
}


export class LoginPage extends React.Component<Prop, LoginState>{

    constructor() {
        super()
        console.log("Login constructor")
    }

    onSuccess() {
        store.dispatch(me.loadProfile())
    }

    logout() {
        store.dispatch(me.triggerLogout()).then(() => {
            hashHistory.replace("auth/login")
        })
    }

    render() {
        const loginForm = (
            <Row>
                <Col xs={12} sm={4} md={3}>
                    <zform.Form action="me/auth" method="PUT" onSuccess={this.onSuccess.bind(this)}>
                        <zform.Hidden name="userTypeName" value="general" />
                        <zform.Hidden name="userPlatformName" value="general" />
                        <zform.TextField name="username" label="Username (Email)" />
                        <zform.Password name="password" label="Password" enterSubmit={true} />
                        <zform.Submit value="Login" />
                    </zform.Form>
                    <h1></h1>
                    <Link to="auth/register">New User Register</Link>
                </Col>
            </Row>
        )
        const onlineRedirectPanel = (
            <Panel header="current logged in">
                <Link to="/">Click here to Index</Link>
            </Panel>
        )
        const wrongUserTypePanel = (
            <Panel header="current logged in">
                <p>You have logged in, but you are not general user.</p>
                <a onClick={this.logout.bind(this)}>Click here to log out.</a>
            </Panel>
        )
        let panel = null
        if (me.isLogged() == false) {
            panel = loginForm
        } else if (me.isGeneralUser()) {
            panel = onlineRedirectPanel
        } else {
            panel = wrongUserTypePanel
        }
        return (
            <div className="container">
                <h1>User Login</h1>
                {panel}
            </div>
        )
    }
}

function select(state: AppState): Prop {
    return { me: state.me }
}

export const Login = connect(select)(LoginPage)