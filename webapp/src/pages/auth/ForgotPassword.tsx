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

interface State {
    sendOut: boolean
}

class ForgotPasswordPage extends React.Component<Prop, State>{

    constructor() {
        super()
        this.state = { sendOut: false }
    }

    onSuccess() {
        this.setState({ sendOut: true })
    }

    tryAgain() {
        this.setState({ sendOut: false })
    }

    render() {
        const resetPanel = (
            <div>
                <p>Enter the e-mail associated with your account, then click Continue. We'll send you a link to a page where you can easily create a new password.</p>
                <zform.Form action="me/password-reset-applications" method="POST" onSuccess={this.onSuccess.bind(this)}>
                    <zform.TextField name="email" label="Email" enterSubmit={true} />
                    <zform.Submit value="Continue" />
                </zform.Form>
            </div>
        )
        const finishPanel = (
            <Panel header="Submit Finish" bsStyle="success">
                <p>You will receive an email from us with instructions for resetting your password. If you don't receive this email, please check your junk mail folder or visit our Help pages to contact Customer Service for further assistance.</p>
                <Button onClick={this.tryAgain.bind(this)}>Can't get the E-mail?</Button>
            </Panel>
        )
        const panel = this.state.sendOut ? finishPanel : resetPanel
        return (
            <div className="container">
                <h1>Password Assistance</h1>
                {panel}
                <h1></h1>
                <p><Link to="auth/register">New User Register</Link></p>
                <p><Link to="auth/login">Login</Link></p>
                <p><Link to="/">Back To Home Page</Link></p>
            </div>
        )
    }
}

function select(state: AppState): Prop {
    return { me: state.me }
}

export const ForgotPassword = connect(select)(ForgotPasswordPage)