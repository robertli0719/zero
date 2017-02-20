import * as React from "react"
import * as ReactDOM from "react-dom"
import { store } from "../../Store"
import { Button, ButtonToolbar, FormControl, FormGroup, Col } from "react-bootstrap"
import * as test from "../../actions/test"
import * as zform from "../../components/zero/zform/zform"
import { http, RestErrorDto } from "../../utilities/http"

interface TestState {
}

type TestDto = {
    username: string,
    password: string,
    like: boolean,
    agree: boolean
}

export class TestIndex extends React.Component<{}, TestState>{

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
        return (
            <div className="container">
                <h1>Test</h1>

                <Col sm={3}>
                    <zform.Form onSubmit={this.submit.bind(this)}>
                        <zform.Hidden name="hiddenValue" value="123" />
                        <zform.TextField label="username" name="username" enterSubmit={true} />
                        <zform.Password label="password" name="password" />
                        <zform.CheckBox label="I like it" name="like" />
                        <zform.CheckBox label="agree to get email" name="agree" />
                        <zform.Radios name="item" value="v1" selections={[
                            {label:"item1",value:"v1"},
                            {label:"item2",value:"v2"},
                            {label:"item3",value:"v3"}
                        ]} />
                        <zform.Select name="item" value="v1" selections={[
                            {label:"item1",value:"v1"},
                            {label:"item2",value:"v2"},
                            {label:"item3",value:"v3"}
                        ]} />
                        <zform.Textarea label="your feedback" name="feedback" />
                        {/*<zform.Image label="imgUrl" name="imgUrl" />*/}
                        <zform.Submit />
                    </zform.Form>
                    <hr />

                    {/*<zform.Form action="test/demos" method="POST" onSuccess={this.onSuccess.bind(this)}>
                        <zform.TextField label="name" name="name" />
                        <zform.TextField label="name1" name="name1" />
                        <zform.TextField label="name2" name="name2" />
                        <zform.TextField label="name3" name="name3" enterSubmit={true} />
                        <zform.Submit />
                    </zform.Form>*/}
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