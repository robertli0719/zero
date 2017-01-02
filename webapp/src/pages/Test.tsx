import * as React from "react"
import * as ReactDOM from "react-dom"
import { store } from "../Store"
import { Button, ButtonToolbar, FormControl, FormGroup, Col } from "react-bootstrap"
import * as test from "../actions/test"
import * as zform from "../components/zero/ZForm"
import { http, RestErrorDto } from "../utilities/http"

interface TestState {
}

type TestDto = {
    username: string,
    password: string,
    like: boolean,
    agree: boolean
}

export class Test extends React.Component<{}, TestState>{

    constructor(props: {}) {
        super(props);
        this.state = {};
    }

    submit(data: TestDto) {
        console.log(data);
        console.log(data.username);
        console.log(data.password);
        console.log(data.agree);
        console.log(data.like);

    }

    onSuccess(data: any) {
        console.log("success", data);
    }

    render() {
        let options = { "1": "apple", "2": "orange" }
        return (
            <div className="container">
                <h1>Test</h1>

                <Col sm={3}>
                    <zform.Form onSubmit={this.submit.bind(this)}>
                        <zform.Hidden name="hiddenValue" value="123" />
                        <zform.TextField label="username" name="username" />
                        <zform.Password label="password" name="password" />
                        <zform.CheckBox label="I like it" name="like" />
                        <zform.CheckBox label="agree to get email" name="agree" />
                        <hr />
                        <zform.Radio label="item1" name="item" value="v1" />
                        <zform.Radio label="item2" name="item" value="v2" />
                        <zform.Radio label="item3" name="item" value="v3" />
                        <hr />
                        <zform.Select label="Select Type" name="type" options={options} />
                        <zform.Textarea label="your feedback" name="feedback" />
                        <hr />
                        <zform.File label="image" name="image" multiple={true} />
                        <zform.Submit />
                    </zform.Form>
                    <hr />

                    <zform.Form action="test/demos" method="POST" onSuccess={this.onSuccess.bind(this)}>
                        <p>POST Demo</p>
                        <zform.TextField label="name" name="name" />
                        <zform.Submit />
                    </zform.Form>
                </Col>
                <Col sm={9}></Col>


            </div>
        );
    }
}

// <form action="api/v1/images" method="POST" encType="multipart/form-data">
//     <input name="file" type="file" multiple={true} />
//     <input type="submit" />
// </form>