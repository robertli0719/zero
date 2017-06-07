import * as React from "react"
import * as ReactDOM from "react-dom"
import { Button, ButtonToolbar } from "react-bootstrap"
import { hashHistory } from "react-router"

interface IndexState {
}

export class Index extends React.Component<{}, IndexState>{

    constructor() {
        super()
    }

    render() {
        return (
            <div className="container">
                <h1>Hello World~!</h1>
                <ButtonToolbar>
                    <Button onClick={hashHistory.push.bind(this, "test")}>Test</Button>
                    <Button onClick={hashHistory.push.bind(this, "admin")}>Admin Dashboard</Button>
                    <Button bsStyle="success">hello</Button>
                </ButtonToolbar>
            </div>
        )
    }
}
