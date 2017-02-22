import * as React from "react";
import { connect } from "react-redux"
import { store, AppState } from "../../Store"
import { UserProfile } from "../../reducers/me"
import * as me from "../../actions/me"
import { Button, ButtonToolbar, Row, Col, Panel, Alert } from "react-bootstrap";
import * as zform from "../../components/zero/zform/zform"
import * as ztable from "../../components/zero/ZTable"
import { http, RestErrorDto } from "../../utilities/http"

type AdminUserDto = {
    id: number
    username: string
    password: string
    locked: boolean
    root: boolean
}

interface Props {
    me: UserProfile
}

interface State {
    adminUserList: AdminUserDto[]
}

class AdminUserAdminComponent extends React.Component<Props, State>{

    constructor(prop: Props) {
        super(prop);
        this.state = { adminUserList: [] }
        this.updateData();
    }

    updateData() {
        return http.get("admin-users")
            .then((adminUserList: AdminUserDto[]) => {
                this.setState({ adminUserList: adminUserList });
            });
    }

    onAddSuccess() {
        this.updateData()
    }

    onDelete(adminUser: AdminUserDto) {
        const url = "admin-users/" + adminUser.username
        http.delete(url)
            .then(() => {
                this.updateData();
            })
            .catch((error: RestErrorDto) => {
                console.log(error);
            })
    }

    onTableButtonRender(adminUser: AdminUserDto): boolean {
        return adminUser.root == false;
    }

    getUserEditorRow() {
        if (me.isAdminRoot() == false) {
            return (
                <Alert bsStyle="warning">
                    <strong>Tips!</strong> For showing more forms, please login with root permission
                </Alert>
            )
        }
        return (
            <Row>
                <Col sm={4}>
                    <Panel header="Reset Admin Password" bsStyle="danger">
                        <zform.Form action="admin-users/{username}/password" method="PUT" successMessage="reset password" onSuccess={this.updateData.bind(this)}>
                            <zform.TextField label="username" name="username" place="pathAndDto" />
                            <zform.Password label="password" name="password" enterSubmit={true} />
                            <zform.Submit value="reset" />
                        </zform.Form>
                    </Panel>
                </Col>
                <Col sm={4}>
                    <Panel header="Add root Permission" bsStyle="danger">
                        <zform.Form action="admin-users/{username}/root" method="PUT" successMessage="add root permission" onSuccess={this.updateData.bind(this)}>
                            <zform.TextField label="username" name="username" place="pathAndDto" enterSubmit={true} />
                            <zform.Submit value="add" />
                        </zform.Form>
                    </Panel>
                </Col>
                <Col sm={4}>
                    <Panel header="Delete root Permission" bsStyle="danger">
                        <zform.Form action="admin-users/{username}/root" method="DELETE" successMessage="add root permission" onSuccess={this.updateData.bind(this)}>
                            <zform.TextField label="username" name="username" place="pathAndDto" enterSubmit={true} />
                            <zform.Submit value="delete" />
                        </zform.Form>
                    </Panel>
                </Col>
            </Row>
        )
    }

    render() {
        return (
            <div className="container">
                <h1>Admin Editor</h1>
                <Row>
                    <Col sm={3}>
                        <Panel header="Add Admin" bsStyle="primary">
                            <zform.Form action="admin-users" method="POST" onSuccess={this.onAddSuccess.bind(this)}>
                                <zform.TextField label="username" name="username" enterSubmit={false} />
                                <zform.Password label="password" name="password" enterSubmit={true} />
                                <zform.Hidden name="locked" value="false" />
                                <zform.Hidden name="root" value="false" />
                                <zform.Submit value="add" />
                            </zform.Form>
                        </Panel>
                    </Col>
                    <Col sm={9}>
                        <Panel header="Admin List" bsStyle="primary">
                            <ztable.Table dtoList={this.state.adminUserList} >
                                <ztable.ColButton name="delete" bsStyle="danger" bsSize="xs"
                                    onAction={this.onDelete.bind(this)}
                                    onRender={this.onTableButtonRender.bind(this)} />
                            </ztable.Table>
                        </Panel>
                    </Col>
                </Row>
                {this.getUserEditorRow()}
            </div>
        );
    }
}

function select(state: AppState): Props {
    return { me: state.me };
}

export let AdminUserAdmin = connect(select)(AdminUserAdminComponent);