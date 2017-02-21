import * as React from "react"
import { connect } from "react-redux"
import { store, AppState } from "../../Store"
import { Button, ButtonToolbar, FormControl, FormGroup, Col } from "react-bootstrap"
import * as zform from "../../components/zero/zform/zform"


let testForm: zform.TagSchema = {
    type: "object", children: [
        { type: "textfield", label: "Username", name: "username" },
        { type: "password", label: "Password", name: "password" },
        { type: "textfield", label: "Num", name: "num", value: 123, enterSubmit: true },
        { type: "textarea", label: "Content", name: "content", value: "input here..." },
        { type: "hidden", name: "pid", value: 123 },
        { type: "hidden", name: "hidden_token", value: "TOKEN123" },
        { type: "checkbox", name: "like", label: "Like" },
        { type: "checkbox", name: "useful", value: true, label: "Useful" },
        {
            type: "radios", name: "radio_item", label: "Radio items", selections: [
                { label: "Item1", value: "v1" },
                { label: "Item2", value: "v2" },
                { label: "Item3", value: "v3" }
            ]
        },
        {
            type: "select", name: "select_item", label: "Select items", selections: [
                { label: "Item1", value: "v1" },
                { label: "Item2", value: "v2" },
                { label: "Item3", value: "v3" }
            ]
        },
        {
            type: "object", name: "attribute", children: [
                { type: "textfield", label: "Benifets", name: "benifets" },
                { type: "textfield", label: "val", name: "val", value: 456 },
                { type: "hidden", name: "pid", value: 789 },
            ]
        },
        {
            type: "object", name: "company", label: "Company", children: [
                { type: "textfield", label: "Name", name: "name" },
                { type: "textfield", label: "Telephone", name: "telephone" },
                {
                    type: "object", label: "Address", name: "address", children: [
                        { type: "textfield", label: "City", name: "city" },
                        { type: "textfield", label: "State", name: "state" },
                        { type: "textfield", label: "Line1", name: "line1" },
                        { type: "textfield", label: "Line2", name: "line2" },
                    ]
                }
            ]
        },
        { type: "submit", value: "add" }
    ]

}

let testForm2: zform.TagSchema = {
    type: "object", children: [
        { type: "textfield", label: "Username", name: "username" },
        { type: "password", label: "Password", name: "password" },
        { type: "textfield", label: "Num", name: "num", value: 123, enterSubmit: true },
        {
            type: "object", name: "attribute", children: [
                { type: "textfield", label: "Benifets", name: "benifets" },
                { type: "textfield", label: "val", name: "val", value: 456 },
                { type: "hidden", name: "pid", value: 789 },
            ]
        },
        { type: "submit", label: "OK" }
    ]
}

type Prop = {
    val: number
}

export class TestFormPage extends React.Component<Prop, {}>{

    constructor(props: Prop) {
        super(props);
    }

    render() {
        return (
            <div className="container">
                <h1>Test zform</h1>
                <Col xs={6} md={3}>
                    {/*<zform.Form schema={testForm2} />*/}
                    <zform.Form action="test/demos" >
                        <zform.TextField name="name" label="Username" />
                        <zform.Object name="subItem" label="SubItem">
                            <zform.TextField name="num" label="Number" />
                            <zform.TextField name="language" label="Language" />
                        </zform.Object>
                        <zform.Object name="aaa" schema={testForm2} />
                        <zform.ImageShower name="imgUrl" />
                        <zform.Image label="Product Image" name="imgUrl" />
                        <zform.Submit value="Add" />
                    </zform.Form>
                </Col>
                <div>{this.props.val}</div>
            </div>
        );
    }
}


function select(state: AppState): Prop {
    return { val: state.test.val };
}

export let TestForm = connect(select)(TestFormPage);

