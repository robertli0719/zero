import * as React from "react"
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
        let me = this.props.me
        return <div>
            <h1>User: {me.uid}</h1>
            <p>Name: {me.name}</p>
            <p>Telephone: {me.telephone}</p>
        </div>
    }
}

function select(state: AppState): Prop {
    return { me: state.me }
}

export const Me = connect(select)(MePage)