import * as React from "react";
import { connect } from "react-redux"
import { hashHistory } from "react-router"
import { Button, ButtonToolbar, ControlLabel, FormControl, Form, FormGroup, Checkbox, Col, Row, Panel } from "react-bootstrap"
import { Link } from "react-router"
import * as me from "../../actions/me"
import * as zform from "../../components/zero/zform/zform"
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
}

export class StaffLoginPage extends React.Component<Props, StaffLoginState>{

    constructor(props: Props) {
        super(props);
        let platform = this.props.params['platform'];
        this.state = {
            userPlatformName: platform,
        }
        console.log("staffLogin:", platform);
        console.log("StaffLoginPage constructor");
    }

    onSuccess() {
        store.dispatch(me.loadProfile());
    }

    logout() {
        store.dispatch(me.triggerLogout()).then(() => {
            const loginPath = '/dashboard/' + this.state.userPlatformName + "/login";
            hashHistory.replace(loginPath);
        })
    }

    render() {
        let loginForm = (
            <Row>
                <Col xs={12} sm={4} md={3}>
                    <zform.Form action="me/auth" method="PUT" onSuccess={this.onSuccess.bind(this)}>
                        <zform.Hidden name="userTypeName" value="staff" />
                        <zform.Hidden name="userPlatformName" value={this.state.userPlatformName} />
                        <zform.TextField name="username" label="Username" />
                        <zform.Password name="password" label="Password" enterSubmit={true} />
                        <zform.Submit value="Login" />
                    </zform.Form>
                </Col>
            </Row>
        )
        const indexUrl = '/dashboard/' + this.state.userPlatformName + "/index";
        let onlineRedirectPanel = (
            <Panel header="current logged in">
                <Link to={indexUrl}>Click here to dashboard</Link>
            </Panel>
        )
        let wrongUserTypePanel = (
            <Panel header="current logged in">
                <p>You have logged in, but you are not a user of {this.state.userPlatformName}</p>
                <a onClick={this.logout.bind(this)}>Click here to log out.</a>
            </Panel>
        )
        let panel = null;
        if (me.isLogged() == false) {
            panel = loginForm;
        } else if (me.isPlatformUser(this.state.userPlatformName)) {
            panel = onlineRedirectPanel;
        } else {
            panel = wrongUserTypePanel
        }
        return (
            <div className="container">
                <h1>{this.state.userPlatformName} User Login</h1>
                {panel}
            </div>
        );
    }
}

function select(state: AppState): ReduxProps {
    return { me: state.me };
}

export let StaffLogin = connect(select)(StaffLoginPage);