import * as _ from "lodash"
import * as React from "react"
import * as ReactDOM from "react-dom"
import { store } from "../../Store"
import { Button, ButtonToolbar, FormControl, FormGroup, Col } from "react-bootstrap"
import * as test from "../../actions/test"
import * as zform from "../../components/zero/zform/zform"
import * as zformSchema from "../../components/zero/zform/zform_schema"
import { http, RestErrorDto } from "../../utilities/http"

interface TestState {
    options: zformSchema.Selection[]
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
        const options = [
            { label: "item1", value: "v1" },
            { label: "item2", value: "v2" },
            { label: "item3", value: "v3" }
        ]
        this.state = { options: options }
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

    addOption() {
        // const options: zformSchema.Selection[] = []
        // for (let i = 0; i < this.state.options.length + 3; i++) {
        //     options.push({ label: "newitem" + i, value: "newitem" + i })
        // }
        // this.setState({ options: options })

        this.state.options.push({ label: "newitem", value: "newitem" + this.state.options.length })
        this.setState({})
    }

    render() {
        console.log("TestIndex render")
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
                        <zform.Radios name="item" value="v1" selections={this.state.options} />
                        <zform.Select name="item" value="v1" selections={this.state.options} />
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
                {this.state.options.map((item) => {
                    return <p>{item.label}:{item.value}</p>
                })}
                <ButtonToolbar>
                    <Button onClick={this.addOption.bind(this)}>add option</Button>
                </ButtonToolbar>
            </div>
        );
    }
}

// <form action="api/v1/images" method="POST" encType="multipart/form-data">
//     <input name="file" type="file" multiple={true} />
//     <input type="submit" />
// </form>