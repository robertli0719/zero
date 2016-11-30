/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
import * as React from "react";
import * as ReactDOM from "react-dom";
import { Button, ButtonToolbar, FormControl, FormGroup, ControlLabel } from "react-bootstrap";
import { LinkContainer } from 'react-router-bootstrap';
import './zero.scss';

export interface Field {
    label: string
    name: string
    type: string
    value: string
}

interface ZFormProps {
    action: string
    submit: string
    fields: Field[]
}

interface ZFormState {
    fieldVals: { [key: string]: string },
    fieldErrors: { [key: string]: string },
    globalErrors: string[]
}

export class Form extends React.Component<ZFormProps, ZFormState>{

    constructor(props: ZFormProps) {
        super(props);
        this.state = { fieldVals: {}, fieldErrors: {}, globalErrors: [] };
        for (let field of this.props.fields) {
            this.state.fieldVals[field.name] = "";
        }
    }

    fieldChangeHandler(event: React.FormEvent<HTMLInputElement>) {
        let target = event.currentTarget;
        let name = target.name;
        let val = target.value;
        this.state.fieldVals[name] = val;
        this.setState(this.state);
    }

    formSubmitSuccessHandler(feedback: any) {
        console.log("success:", feedback, feedback.length);
        this.state.fieldErrors = {};
        this.state.globalErrors = [];
        this.setState(this.state);
    }

    makeFieldNameSet() {
        let nameSet: { [key: string]: Boolean } = {};
        this.props.fields.map((item) => {
            nameSet[item.name] = true;
        });
        return nameSet;
    }

    formSubmitErrorHandler(feedback: any) {
        console.log("formSubmitErrorHandler start");
        this.state.fieldErrors = {};
        this.state.globalErrors = [];
        switch (feedback.status) {
            case 400:
                let fieldNameSet = this.makeFieldNameSet();
                let result = JSON.parse(feedback.responseText);
                let fieldErrors = result["fieldErrors"];
                for (let field in fieldErrors) {
                    let val = fieldErrors[field];
                    if (field in fieldNameSet) {
                        this.state.fieldErrors[field] = val;
                    } else {
                        console.log(val);
                        this.state.globalErrors.push(val);
                    }
                }
                for (let val of result["globalErrors"]) {
                    this.state.globalErrors.push(val);
                }
                break;
            default:
                console.log("Error:Fail to submit");
        }
        this.setState(this.state);
        console.log("formSubmitErrorHandler end");
    }

    submit() {
        let json = JSON.stringify(this.state.fieldVals);
        try {
            $.ajax({
                url: this.props.action,
                method: "post",
                contentType: "application/json;charset=UTF-8",
                data: json,
                success: this.formSubmitSuccessHandler.bind(this),
                error: this.formSubmitErrorHandler.bind(this)
            });
        } catch (error) {
            console.log("catch the error");
        }
    }

    render() {

        return <form>
            {
                this.state.globalErrors.map((val) => {
                    return <p>{val}</p>
                })
            }
            {

                this.props.fields.map((item) => {
                    return (
                        <FormGroup>
                            <ControlLabel>{item.label}</ControlLabel>
                            <FormControl
                                type={item.type}
                                name={item.name}
                                value={this.state.fieldVals[item.name]}
                                onChange={this.fieldChangeHandler.bind(this)}
                                />
                            <FormControl.Feedback />
                            <p>{this.state.fieldErrors[item.name]}</p>
                        </FormGroup>
                    )
                })
            }
            <Button bsStyle="success" className="pull-right" onClick={this.submit.bind(this)}>
                {this.props.submit}
            </Button>
        </form>
    }
}