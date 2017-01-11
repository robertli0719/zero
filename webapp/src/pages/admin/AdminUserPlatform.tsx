import * as React from "react";
import { connect } from "react-redux"
import { hashHistory } from 'react-router'
import { store, AppState } from "../../Store"
import { Button, ButtonToolbar, Row, Col, Panel } from "react-bootstrap";
import * as zform from "../../components/zero/ZForm"
import * as ztable from "../../components/zero/ZTable"
import { http } from "../../utilities/http"

type UserPlatformDto = {
    name: string
    userTypeName: string
}

interface Prop {
}

interface State {
    userTypesOptions: { [key: string]: any }
    userPlatformList: UserPlatformDto[]
}

class AdminUserPlatformComponent extends React.Component<Prop, State>{

    constructor(prop: Prop) {
        super(prop);
        this.state = { userTypesOptions: {}, userPlatformList: [] }
        this.updateData();
    }

    updateData() {
        return http.get("user-platforms")
            .then((userPlatformList: UserPlatformDto[]) => {
                this.state.userPlatformList = userPlatformList;
                this.setState(this.state);
            }).then(() => {
                return zform.fetchSelectOptions("user-types")
            }).then((options) => {
                this.state.userTypesOptions = options;
                this.setState(this.state);
            });
    }

    onAddSuccess() {
        this.updateData()
    }

    onDelete(dto: UserPlatformDto, index: number) {
        const url = "user-platforms/" + dto.name
        return http.delete(url)
            .then(() => {
                return this.updateData();
            })
    }

    redirectToUserPage(dto: UserPlatformDto, index: number) {
        hashHistory.replace("admin/user-staff/" + dto.name);
    }

    render() {
        return (
            <div className="container">
                <h1>User Platform Editor</h1>
                <Row>
                    <Col sm={3}>
                        <Panel header="Add Platform" bsStyle="primary">
                            <zform.Form action="user-platforms" method="POST" onSuccess={this.onAddSuccess.bind(this)}>
                                <zform.Select label="type" name="userTypeName" options={this.state.userTypesOptions} />
                                <zform.TextField label="name" name="name" enterSubmit={true} />
                                <zform.Submit value="add" />
                            </zform.Form>
                        </Panel>
                    </Col>
                    <Col sm={9}>
                        <Panel header="Platform List" bsStyle="primary">
                            <ztable.Table dtoList={this.state.userPlatformList} >
                                <ztable.ColButton name="delete" bsStyle="danger" bsSize="xs" onAction={this.onDelete.bind(this)} />
                                <ztable.ColButton name="users" bsStyle="success" bsSize="xs" onAction={this.redirectToUserPage.bind(this)} />
                            </ztable.Table>
                        </Panel>
                    </Col>
                </Row>
            </div>
        );
    }
}

function select(state: AppState): Prop {
    return {};
}

export let AdminUserPlatform = connect(select)(AdminUserPlatformComponent);