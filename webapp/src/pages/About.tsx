import * as React from "react";
import * as ReactDOM from "react-dom";
import { connect } from "react-redux"
import { AppState } from "../Store"

interface Prop {
    val: number
}

class AboutPage extends React.Component<Prop, {}>{

    constructor() {
        super();
    }

    render() {
        let num = this.props.val;
        return (
            <div className="container">
                <h1>About us</h1>
                <p>{num}</p>
            </div>
        );
    }
}

function select(state: AppState): Prop {
    return { val: state.val };
}

export let About = connect(select)(AboutPage);