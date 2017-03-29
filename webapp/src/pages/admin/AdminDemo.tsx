import * as React from "react";
import { connect } from "react-redux"
import { store, AppState } from "../../Store"
import { Button, ButtonToolbar, Row, Col, Panel } from "react-bootstrap";
import * as zform from "../../components/zero/zform/zform"
import { http, RestErrorDto } from "../../utilities/http"

interface Props {
}

interface State {
}

class AdminDemoComponent extends React.Component<Props, State>{

    constructor(prop: Props) {
        super(prop);
    }

    appInit() {
    }

    render() {
        return (
            <div className="container">
                <h1>Admin Page Demo</h1>
                <ButtonToolbar>
                    <Button bsStyle="success" onClick={this.appInit.bind(this)}>OK</Button>
                </ButtonToolbar>
            </div>
        );
    }
}

function select(state: AppState): Props {
    return {};
}

export let AdminDemo = connect(select)(AdminDemoComponent);