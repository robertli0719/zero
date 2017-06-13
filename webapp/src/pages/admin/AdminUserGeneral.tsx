import * as React from "react"
import { connect } from "react-redux"
import { store, AppState } from "../../Store"
import { Button, ButtonToolbar } from "react-bootstrap"
import * as zform from "../../components/zero/zform/zform"
import * as zview from "../../components/zero/zview/zview"

interface Prop {
}

interface State {
}

class AdminUserGeneralComponent extends React.Component<Prop, State>{

    constructor(prop: Prop) {
        super(prop)
    }

    render() {
        return (
            <div className="container">
                <h1>User Management</h1>
                <zview.View header="General User List" bsStyle="primary" uri="users">
                </zview.View>
            </div>
        )
    }
}

function select(state: AppState): Prop {
    return {}
}

export const AdminUserGeneral = connect(select)(AdminUserGeneralComponent)