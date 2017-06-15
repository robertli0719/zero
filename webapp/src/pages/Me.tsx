import * as React from "react"
import { connect } from "react-redux"
import { hashHistory } from "react-router"
import { Button, ButtonToolbar, ControlLabel, FormControl, Form, FormGroup, Checkbox, Col, Row, Panel } from "react-bootstrap"
import * as me from "../actions/me"
import * as zform from "../components/zero/zform/zform"
import * as utilities from "../utilities/coder"
import { store, AppState } from "../Store"
import { UserProfile } from "../reducers/me"
import { RestErrorDto } from "../utilities/http"
import { MobileBindingPanel } from "../components/auth/MobileBindingPanel"


interface Prop {
    me: UserProfile
}

interface State {
}

export class MePage extends React.Component<Prop, State>{

    constructor() {
        super()
        console.log("MePage constructor")
    }

    onSuccess() {
        store.dispatch(me.loadProfile())
    }

    render() {
        const me = this.props.me
        return (
            <div className="container">
                <h1>Welcome back, {me.name}</h1>
                <p>account: {me.authLabel}</p>
                <p>user Id: {me.uid}</p>
                <MobileBindingPanel me={me} />
            </div >
        )
    }
}

function select(state: AppState): Prop {
    return { me: state.me }
}

export const Me = connect(select)(MePage)