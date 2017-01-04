import * as React from "react";
import { connect } from "react-redux"
import { Button, ButtonToolbar, ControlLabel, FormControl, Form, FormGroup, Checkbox, Col, Row, Panel } from "react-bootstrap"
import { Link } from "react-router"
import * as me from "../../actions/me"
import * as zform from "../../components/zero/ZForm"
import * as utilities from "../../utilities/random-coder"
import { store, AppState } from "../../Store"
import { UserProfile } from "../../reducers/me"
import { RestErrorDto } from "../../utilities/http"

type ReduxProps = {
    me: UserProfile
}

type CommonProps = {
    params: any
}

type Props = ReduxProps & CommonProps;

type StaffLoginState = {
    userPlatformName: string
    userTypeName: string
}

export class StaffLoginPage extends React.Component<Props, StaffLoginState>{

    constructor(props: Props) {
        super(props);
        let platform = this.props.params['platform'];
        this.state = {
            userPlatformName: platform,
            userTypeName: "staff"
        }
        console.log("staffLogin:", platform);

        console.log("StaffLoginPage constructor");
    }

    onSuccess() {
        store.dispatch(me.loadProfile());
    }

    render() {
        let loginForm = (
            <Row>
                <Col xs={12} sm={4} md={3}>
                    <zform.Form action="me/auth" method="PUT" onSuccess={this.onSuccess.bind(this)}>
                        <zform.Hidden name="userTypeName" value="admin" />
                        <zform.Hidden name="userPlatformName" value="admin" />
                        <zform.TextField name="username" label="Username" />
                        <zform.Password name="password" label="Password" enterSubmit={true} />
                        <zform.Submit value="Login" />
                    </zform.Form>
                </Col>
            </Row>
        )
        let redirectPanel = (
            <Panel header="current logged in">
                <Link to="admin/index">Click here to dashboard</Link>
            </Panel>
        )
        let panel = me.isAdmin() ? redirectPanel : loginForm;
        return (
            <div className="container">
                <h1>Store User Login</h1>
                {panel}
            </div>
        );
    }
}

function select(state: AppState): ReduxProps {
    return { me: state.me };
}

export let StaffLogin = connect(select)(StaffLoginPage);