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
import { http, RestErrorDto } from "../../utilities/http"


interface Prop {
    me: UserProfile
}

interface State {
    verifyEmail: string
    submitEmail: string
}

export class RegisterPage extends React.Component<Prop, State>{

    constructor() {
        super()
        this.state = { verifyEmail: null, submitEmail: null }
    }

    beforeSubmitRegister(dto: any) {
        let email = dto.email
        this.setState({ submitEmail: email })
    }

    afterSubmitRegister() {
        this.setState({ verifyEmail: this.state.submitEmail, submitEmail: null })
    }

    resendVerfiyEmail() {
        if (!this.state.verifyEmail) {
            return
        }
        let uri = "me/registers/verifications/sender?email=" + this.state.verifyEmail
        http.post(uri, null).then(() => {
            this.setState({})
        })
    }

    logout() {
        store.dispatch(me.triggerLogout()).then(() => {
            hashHistory.replace("auth/login")
        })
    }

    render() {
        const registerForm = (
            <Row>
                <Col xs={12} sm={4} md={3}>
                    <zform.Form action="me/registers" method="POST" onSubmit={this.beforeSubmitRegister.bind(this)} onSuccess={this.afterSubmitRegister.bind(this)}>
                        <zform.TextField name="email" label="Email" />
                        <zform.Password name="password" label="Password" enterSubmit={true} />
                        <zform.Password name="reenterPassword" label="Re-enter Password" />
                        <zform.TextField name="name" label="Name" enterSubmit={true} />
                        <zform.Submit value="Submit" />
                    </zform.Form>
                    <h1></h1>
                    <Link to="auth/login">Login</Link>
                </Col>
            </Row>
        )
        const verifyRegisterPanel = (
            <Panel header="Verify Panel">
                <p>Please check your email to activate your account. </p>
                <p>Your email is {this.state.verifyEmail}</p>
                <p>If you can't get the email, please click
                    <a onClick={this.resendVerfiyEmail.bind(this)}> here </a>
                    to resend it again.
                </p>
            </Panel>
        )

        const logoutPanel = (
            <Panel header="current logged in">
                <p>You have logged in</p>
                <a onClick={this.logout.bind(this)}>Click here to log out.</a>
            </Panel>
        )
        let panel = null
        if (this.state.verifyEmail != null) {
            panel = verifyRegisterPanel
        } else if (me.isLogged()) {
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