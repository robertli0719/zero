import * as React from "react"
import { connect } from "react-redux"
import { hashHistory } from "react-router"
import { Button, ButtonToolbar, ControlLabel, FormControl, Form, FormGroup, Checkbox, Col, Row, Panel } from "react-bootstrap"
import { Link } from "react-router"
import * as me from "../../actions/me"
import * as zform from "../../components/zero/zform/zform"
import * as utilities from "../../utilities/random-coder"
import { store, AppState } from "../../Store"
import { UserProfile } from "../../reducers/me"
import { RestErrorDto } from "../../utilities/http"


interface Prop {
    me: UserProfile
}

interface State {
}

export class RegisterPage extends React.Component<Prop, State>{

    constructor() {
        super()
        console.log("Register constructor")
    }

    onSuccess() {
        store.dispatch(me.loadProfile())
    }

    logout() {
        store.dispatch(me.triggerLogout()).then(() => {
            hashHistory.replace("admin/login")
        })
    }

    render() {
        const registerForm = (
            <Row>
                <Col xs={12} sm={4} md={3}>
                    <zform.Form action="me/register" method="POST" onSuccess={this.onSuccess.bind(this)}>
                        <zform.TextField name="email" label="Email" />
                        <zform.Password name="password" label="Password" enterSubmit={true} />
                        <zform.Password name="reenterPassword" label="Re-enter Password" />
                        <zform.Password name="name" label="Name" enterSubmit={true} />
                        <zform.Submit value="Submit" />
                    </zform.Form>
                </Col>
            </Row>
        )
        const onlineRedirectPanel = (
            <Panel header="current logged in">
                <Link to="admin/index">Click here to dashboard</Link>
            </Panel>
        )
        const logoutPanel = (
            <Panel header="current logged in">
                <p>You have logged in</p>
                <a onClick={this.logout.bind(this)}>Click here to log out.</a>
            </Panel>
        )
        let panel = null
        if (me.isLogged()) {
            panel = logoutPanel
        } else {
            panel = registerForm
        }
        return (
            <div className="container">
                <h1>User Register</h1>
                {panel}
            </div>
        )
    }
}

function select(state: AppState): Prop {
    return { me: state.me }
}

export const Register = connect(select)(RegisterPage)