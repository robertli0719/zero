import * as React from "react"
import * as ReactDOM from "react-dom"
import { connect } from "react-redux"
import { AppState } from "../Store"

interface Props {
    val: number
}

class AboutPage extends React.Component<Props, {}>{

    constructor() {
        super()
    }

    render() {
        const num = this.props.val
        return (
            <div className="container">
                <h1>About us</h1>
                <p>{num}</p>
            </div>
        )
    }
}

function select(state: AppState): Props {
    return { val: state.test.val }
}

export const About = connect(select)(AboutPage)