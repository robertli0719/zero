import * as React from "react"
import { connect } from "react-redux"
import { store, AppState } from "../../Store"
import { UserProfile } from "../../reducers/me"
import { Button, ButtonToolbar, Row, Col, Panel, Alert } from "react-bootstrap"
import * as zform from "../../components/zero/zform/zform"

interface Props {
    me: UserProfile
}

export class AdminIndexPage extends React.Component<Props, {}>{

    constructor() {
        super()
    }

    render() {
        return (
            <div className="container">
                <h1>Admin Management System</h1>
                <p>Admin: {this.props.me.authLabel}</p>
                <Row>
                    <Col sm={3}>
                        <Panel header="Reset Password">
                            <zform.Form action="me/auth/password" method="PUT" successMessage="reset password" >
                                <zform.Password name="oldPassword" label="Current password" />
                                <zform.Password name="newPassword" label="New password" />
                                <zform.Password name="reenterNewPassword" label="Reenter new password" />
                                <zform.Submit value="submit" />
                            </zform.Form>
                        </Panel>
                    </Col>
                </Row>
            </div>
        )
    }
}

function select(state: AppState): Props {
    console.log("AdminLogin select", state)
    return { me: state.me }
}

export const AdminIndex = connect(select)(AdminIndexPage)