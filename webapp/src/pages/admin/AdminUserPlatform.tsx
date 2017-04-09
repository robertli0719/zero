import * as React from "react"
import { connect } from "react-redux"
import { hashHistory } from 'react-router'
import { store, AppState } from "../../Store"
import { Button, ButtonToolbar, Row, Col, Panel } from "react-bootstrap"
import * as zform from "../../components/zero/zform/zform"
import * as zview from "../../components/zero/zview/zview"
import { http } from "../../utilities/http"
import { UpdateEventListener } from "../../components/zero/event/UpdateEventListener"

type UserPlatformDto = {
    name: string
    userTypeName: string
}

interface Prop {
}

interface State {
    updateEventListener: UpdateEventListener
}

class AdminUserPlatformComponent extends React.Component<Prop, State>{

    constructor(prop: Prop) {
        super(prop)
        this.state = {
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

    onDelete(dto: UserPlatformDto, index: number) {
        const url = "user-platforms/" + dto.name
        return http.delete(url)
            .then(() => {
                return this.updateData()
            })
    }

    redirectToUserPage(dto: UserPlatformDto, index: number) {
        hashHistory.push("admin/user-staff/" + dto.name)
    }

    onTableButtonRender(dto: UserPlatformDto): boolean {
        return dto.userTypeName == "staff"
    }

    render() {
        return (
            <div className="container">
                <h1>User Platform Editor</h1>
                <Row>
                    <Col sm={3}>
                        <Panel header="Add Platform" bsStyle="primary">
                            <zform.Form action="user-platforms" method="POST" onSuccess={this.onAddSuccess.bind(this)}>
                                <zform.Hidden name="userTypeName" value="staff" />
                                <zform.TextField label="name" name="name" enterSubmit={true} />
                                <zform.Submit value="add" />
                            </zform.Form>
                        </Panel>
                    </Col>
                    <Col sm={9}>
                        <zview.View header="Platform List" bsStyle="primary" uri="user-platforms" updateEventListener={this.state.updateEventListener} >
                            <zview.ColButton name="delete" bsStyle="danger" bsSize="xs"
                                onAction={this.onDelete.bind(this)}
                                onRender={this.onTableButtonRender.bind(this)} />
                            <zview.ColButton name="users" bsStyle="success" bsSize="xs"
                                onAction={this.redirectToUserPage.bind(this)}
                                onRender={this.onTableButtonRender.bind(this)} />
                        </zview.View>
                    </Col>
                </Row>
            </div>
        )
    }
}

function select(state: AppState): Prop {
    return {}
}

export const AdminUserPlatform = connect(select)(AdminUserPlatformComponent)