import * as React from "react"
import { connect } from "react-redux"
import { hashHistory, Link } from "react-router"
import { Button, ButtonToolbar, ControlLabel, FormControl, Form, FormGroup, Checkbox, Col, Row, Panel } from "react-bootstrap"
import * as me from "../../actions/me"
import * as zform from "../../components/zero/zform/zform"
import * as utilities from "../../utilities/coder"
import { store, AppState } from "../../Store"
import { UserProfile } from "../../reducers/me"
import { RestErrorDto } from "../../utilities/http"


type ReduxProps = {
}

type CommonProps = {
    params: any
}

type Props = ReduxProps & CommonProps

interface State {
    success: boolean
    code: string
}

class ResetPasswordPage extends React.Component<Props, State>{

    constructor(props: Props) {
        super(props)
        const code = this.props.params['code']
        this.state = { success: false, code: code }
    }

    onSuccess() {
        this.setState({ success: true })
    }

    render() {
        const inputPanel = (
            <div>
                <h3>Create your new password.</h3>
                <p>We'll ask you for this password when you place an order, check on an order's status, and access other account information.</p>
                <zform.Form action="me/password-resetter" method="POST" onSuccess={this.onSuccess.bind(this)}>
                    <zform.Hidden name="code" value={this.state.code} />
                    <zform.Password name="password" label="Password" />
                    <zform.Password name="reenterPassword" label="Reenter new password" enterSubmit={true} />
                    <zform.Submit value="Save changes" />
                </zform.Form>
            </div>
        )
        const finishPanel = (
            <Panel header="Submit Finish" bsStyle="success">
                <p>You have successfully changed your password.</p>
                <p><Link to="auth/login">Login</Link></p>
            </Panel>
        )
        const panel = this.state.success ? finishPanel : inputPanel
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

function select(state: AppState): ReduxProps {
    return {}
}

export const ResetPassword = connect(select)(ResetPasswordPage)