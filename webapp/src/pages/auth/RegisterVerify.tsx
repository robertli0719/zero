import * as React from "react"
import { connect } from "react-redux"
import { hashHistory, Link } from "react-router"
import { Button, ButtonToolbar, ControlLabel, FormControl, Form, FormGroup, Checkbox, Col, Row, Panel } from "react-bootstrap"
import * as me from "../../actions/me"
import * as zform from "../../components/zero/zform/zform"
import * as utilities from "../../utilities/coder"
import { store, AppState } from "../../Store"
import { UserProfile } from "../../reducers/me"
import { RestErrorDto, http } from "../../utilities/http"


type ReduxProps = {
}

type CommonProps = {
    params: any
}

type Props = ReduxProps & CommonProps

interface State {
    verifiedCode: string
    verifySuccess: boolean
    error: RestErrorDto
}


export class RegisterVerifyPage extends React.Component<Props, State>{

    constructor(props: Props) {
        super(props)
        console.log("RegisterVerify constructor", this.props)
        const code = this.props.params['code']
        this.state = { verifiedCode: code, verifySuccess: false, error: null }
    }

    componentDidMount() {
        const uri = "me/registers/verifications/verifier?code=" + this.state.verifiedCode
        http.post(uri, null).then(() => {
            this.setState({ verifySuccess: true, error: null })
        }).catch((error: RestErrorDto) => {
            this.setState({ verifySuccess: false, error: error })
        })
    }

    render() {
        let panel = null
        if (this.state.verifySuccess) {
            panel = (
                <Panel header="Verify Register Email" bsStyle="primary">
                    <h3>Welcome to use our system.</h3>
                    <Link to="auth/login">Click here to login.</Link>
                </Panel>
            )
        } else if (this.state.error) {
            panel = (
                <Panel header="Verify Register Email" bsStyle="danger">
                    <h3>Errors:</h3>
                    {this.state.error.errors.map((item) => {
                        return <p>{item.message}</p>
                    })}
                </Panel>
            )
        } else {
            panel = (
                <Panel header="Verify Register Email">
                    <p>code:{this.state.verifiedCode}</p>
                    <p>loading...</p>
                </Panel>
            )
        }
        return (
            <div className="container">
                <h1></h1>
                {panel}
            </div>
        )
    }
}

function select(state: AppState): ReduxProps {
    return {}
}

export const RegisterVerify = connect(select)(RegisterVerifyPage)