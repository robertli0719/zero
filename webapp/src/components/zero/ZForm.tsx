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
    onSuccess: Function
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
        this.props.onSuccess(feedback);
    }

    makeFieldNameSet() {
        let nameSet: { [key: string]: Boolean } = {};
        this.props.fields.map((item) => {
            nameSet[item.name] = true;
        });
        return nameSet;
    }

    formSubmitErrorHandler(feedback: any) {
        this.state.fieldErrors = {};
        this.state.globalErrors = [];

        let fieldNameSet = this.makeFieldNameSet();
        let result = JSON.parse(feedback.responseText);
        let errors = result["errors"];
        for (let field in errors) {
            let error = errors[field];
            let msg = error['message'];
            switch (error['type']) {
                case 'FIELD_ERROR':
                    let field = error["source"];
                    if (field in fieldNameSet) {
                        this.state.fieldErrors[field] = msg;
                    } else {
                        this.state.globalErrors.push(msg);
                    }
                    break;
                default:
                    console.log(error);
                    this.state.globalErrors.push(msg);
            }
        }
        this.setState(this.state);
    }

    submit() {
        let json = JSON.stringify(this.state.fieldVals);
        $.ajax({
            url: this.props.action,
            method: "post",
            contentType: "application/json;charset=UTF-8",
            data: json,
            success: this.formSubmitSuccessHandler.bind(this),
            error: this.formSubmitErrorHandler.bind(this)
        });
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