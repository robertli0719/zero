import * as React from "react"
import { connect } from "react-redux"
import { store, AppState } from "../../Store"
import { Button, ButtonToolbar } from "react-bootstrap"

interface Prop {
}

interface State {
}

class ReportViewComponent extends React.Component<Prop, State>{

    constructor(prop: Prop) {
        super(prop)
    }

    appInit() {
    }

    render() {
        return (
            <div className="container">
                <h1>Report View</h1>
                <ButtonToolbar>
                    <Button bsStyle="success" onClick={this.appInit.bind(this)}>OK</Button>
                </ButtonToolbar>
            </div>
        )
    }
}

function select(state: AppState): Prop {
    return {}
}

export const ReportView = connect(select)(ReportViewComponent)