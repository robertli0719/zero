import * as _ from "lodash"
import * as React from "react"
import * as ReactDOM from "react-dom"
import { store } from "../../Store"
import { Button, ButtonToolbar, FormControl, FormGroup, Col } from "react-bootstrap"
import * as test from "../../actions/test"
import * as zform from "../../components/zero/zform/zform"
import * as zformSchema from "../../components/zero/zform/zform_schema"
import { http, RestErrorDto, HttpContent } from "../../utilities/http"

interface TestState { }

type TestDto = {}

export class TestIndex extends React.Component<{}, TestState>{

    constructor(props: {}) {
        super(props)
        this.state = {}
    }

    addOption() {
        console.log("addOption")
        http.getContent("test/demos")
            .then((content: HttpContent) => {
                console.log(content)
            })
    }

    render() {
        return (
            <div className="container">
                <h1>Test</h1>

                <ButtonToolbar>
                    <Button onClick={this.addOption.bind(this)}>add option</Button>
                </ButtonToolbar>
            </div>
        )
    }
}
