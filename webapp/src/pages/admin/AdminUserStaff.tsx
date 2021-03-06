import * as React from "react"
import { connect } from "react-redux"
import { store, AppState } from "../../Store"
import { Button, ButtonToolbar, Row, Col, Panel } from "react-bootstrap"
import * as zform from "../../components/zero/zform/zform"
import * as zview from "../../components/zero/zview/zview"
import { http, RestErrorDto } from "../../utilities/http"
import { UpdateEventListener } from "../../components/zero/event/UpdateEventListener"

type StaffUserDto = {
    id: number
    username: string
    password: string
    locked: boolean
    root: boolean
}

type ReduxProp = {
}

type CommonProp = {
    params: any
}

type Props = ReduxProp & CommonProp

interface State {
    platform: string
    updateEventListener: UpdateEventListener
}

class AdminUserStaffComponent extends React.Component<Props, State>{

    constructor(prop: Props) {
        super(prop)
        const platform = this.props.params['platform']
        this.state = {
            platform: platform,
            updateEventListener: new UpdateEventListener()
        }
        this.updateData()
    }

    updateData() {
        this.state.updateEventListener.trigger()
    }

    onAddSuccess() {
        this.updateData()
    }

    onDelete(staffUser: StaffUserDto) {
        const url = "user-platforms/" + this.state.platform + "/staffs/" + staffUser.username
        http.delete(url)
            .then(() => {
                this.updateData()
            })
            .catch((error: RestErrorDto) => {
                console.log(error)
            })
    }

    render() {
        const uri = "user-platforms/" + this.state.platform + "/staffs"
        return (
            <div className="container">
                <h1>Platform: {this.state.platform}</h1>
                <Row>
                    <Col sm={3}>
                        <Panel header="Add Staff" bsStyle="primary">
                            <zform.Form action="user-platforms/{userPlatformName}/staffs" method="POST" onSuccess={this.onAddSuccess.bind(this)}>
                                <zform.Hidden place="path" name="userPlatformName" value={this.state.platform} />
                                <zform.TextField label="username" name="username" enterSubmit={false} />
                                <zform.Password label="password" name="password" enterSubmit={true} />
                                <zform.Hidden name="locked" value="false" />
                                <zform.Hidden name="root" value="false" />
                                <zform.Submit value="add" />
                            </zform.Form>
                        </Panel>

                    </Col>
                    <Col sm={9}>
                        <zview.View header="Staff List" bsStyle="primary" uri={uri} updateEventListener={this.state.updateEventListener} >
                            <zview.ColButton name="delete" bsStyle="danger" bsSize="xs" onAction={this.onDelete.bind(this)} />
                        </zview.View>
                    </Col>
                </Row>
                <Row>
                    <Col sm={4}>
                        <Panel header="Reset Staff Password" bsStyle="danger">
                            <zform.Form action="user-platforms/{userPlatformName}/staffs/{username}/password" method="PUT" successMessage="reset password">
                                <zform.Hidden place="path" name="userPlatformName" value={this.state.platform} />
                                <zform.TextField label="username" name="username" place="pathAndDto" />
                                <zform.Password label="password" name="password" enterSubmit={true} />
                                <zform.Submit value="reset" />
                            </zform.Form>
                        </Panel>
                    </Col>
                    <Col sm={4}>
                        <Panel header="Add root Permission" bsStyle="danger">
                            <zform.Form action="user-platforms/{userPlatformName}/staffs/{username}/root" method="PUT" successMessage="add root permission" onSuccess={this.updateData.bind(this)}>
                                <zform.Hidden place="path" name="userPlatformName" value={this.state.platform} />
                                <zform.TextField label="username" name="username" place="pathAndDto" enterSubmit={true} />
                                <zform.Submit value="add" />
                            </zform.Form>
                        </Panel>
                    </Col>
                    <Col sm={4}>
                        <Panel header="Delete root Permission" bsStyle="danger">
                            <zform.Form action="user-platforms/{userPlatformName}/staffs/{username}/root" method="DELETE" successMessage="delete root permission" onSuccess={this.updateData.bind(this)}>
                                <zform.Hidden place="path" name="userPlatformName" value={this.state.platform} />
                                <zform.TextField label="username" name="username" place="pathAndDto" enterSubmit={true} />
                                <zform.Submit value="delete" />
                            </zform.Form>
                        </Panel>
                    </Col>
                </Row>
            </div>
        )
    }
}

function select(state: AppState): ReduxProp {
    return {}
}

export const AdminUserStaff = connect(select)(AdminUserStaffComponent)