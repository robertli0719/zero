import * as React from "react";
import { connect } from "react-redux"
import { store, AppState } from "../../Store"
import { UserProfile } from "../../reducers/me"
import { Button, ButtonToolbar } from "react-bootstrap";

interface Props {
    me: UserProfile
}

export class AdminIndexPage extends React.Component<Props, {}>{

    constructor() {
        super();
    }

    appInit() {
    }

    render() {
        return (
            <div className="container">
                <h1>Admin Management System</h1>
                <p>Admin: {this.props.me.authLabel}</p>
                <ButtonToolbar>
                    <Button bsStyle="success" onClick={this.appInit.bind(this)}>OK</Button>
                </ButtonToolbar>
            </div>
        );
    }
}

function select(state: AppState): Props {
    console.log("AdminLogin select", state);
    return { me: state.me };
}

export let AdminIndex = connect(select)(AdminIndexPage);