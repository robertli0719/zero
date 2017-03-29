/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-03-28
 */
import * as React from "react"
import * as rb from "react-bootstrap"

export type Props = {
    bsStyle: string
    title: string
    text: string
}

export class ViewAlert extends React.Component<Props, {}>{

    render() {
        return (
            <rb.Alert bsStyle={this.props.bsStyle}>
                <strong>{this.props.title}</strong>
                {this.props.text}
            </rb.Alert>
        )
    }
}