import * as React from "react"
import * as ReactDOM from "react-dom"
import { Button, ButtonToolbar } from "react-bootstrap"
import * as app from "../../actions/app"
import { store } from "../../Store"
import { http } from "../../utilities/http"

export class AppInit extends React.Component<{}, {}>{

    constructor() {
        super()
    }

    appInit() {
        store.dispatch(app.initApp())
    }

    render() {
        return (
            <div className="container">
                <h1>App Init</h1>
                <ButtonToolbar>
                    <Button bsStyle="success" onClick={this.appInit.bind(this)}>Init</Button>
                </ButtonToolbar>
            </div>
        )
    }
}
