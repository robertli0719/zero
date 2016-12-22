import * as React from "react"
import * as ReactDOM from "react-dom"
import { store } from "../Store"
import { Button, ButtonToolbar, FormControl, FormGroup } from "react-bootstrap"
import * as test from "../actions/test"

interface TestState {
    url: string,
    dateTime: string
}

export class Test extends React.Component<{}, TestState>{

    constructor(props: {}) {
        super(props);
        this.state = { url: "demo/fun", dateTime: null };
    }

    urlInputChange(event: React.FormEvent<HTMLInputElement>) {
        let val = event.currentTarget.value;
        this.state.url = val;
        this.setState(this.state);
    }

    dateInputChange(event: React.FormEvent<HTMLInputElement>) {
        let val = event.currentTarget.value;
        this.state.dateTime = val;
        this.setState(this.state);
    }

    execute(method: string) {

        let data = {
            name: "asdd",
        };
        let json = JSON.stringify(data);
        $.ajax({
            url: this.state.url,
            method: method,
            contentType: "application/json;charset=UTF-8",
            data: json,
            success: function (feedback) {
                console.log("success:", feedback, feedback.length);
            },
            error: function (feedback) {
                console.log("error:", feedback);
            }
        });
    }

    test() {
        console.log("before store.dispatch");
        store.dispatch(test.demo());
        console.log("after store.dispatch");
    }

    render() {
        return (
            <div className="container">
                <h1>Test</h1>
                <p>url: {this.state.url}</p>
                <FormGroup>
                    <label>URL</label>
                    <FormControl type="text" value={this.state.url} onChange={this.urlInputChange.bind(this)} />
                </FormGroup>
                <ButtonToolbar>
                    <Button onClick={this.execute.bind(this, "GET")}>GET</Button>
                    <Button onClick={this.execute.bind(this, "POST")}>POST</Button>
                    <Button onClick={this.execute.bind(this, "PUT")}>PUT</Button>
                    <Button onClick={this.execute.bind(this, "DELETE")}>DELETE</Button>
                </ButtonToolbar>
                <ButtonToolbar>
                    <Button onClick={this.test}>Test</Button>
                </ButtonToolbar>
            </div>
        );
    }
}
