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
    vals: { [key: string]: string }
}

export class Form extends React.Component<ZFormProps, ZFormState>{

    constructor(props: ZFormProps) {
        super(props);
        this.state = { vals: {} };
        for (let field of this.props.fields) {
            this.state.vals[field.name] = "";
        }
    }

    fieldChangeHandler(event: React.FormEvent<HTMLInputElement>) {
        let target = event.currentTarget;
        let name = target.name;
        let val = target.value;
        this.state.vals[name] = val;
        this.setState(this.state);
    }

    submit() {
        for (let key in this.state.vals) {
            let val = this.state.vals[key];
            console.log(key + " : " + val);
        }
    }

    render() {
        return <form>
            {
                this.props.fields.map((item) => {
                    return (
                        <FormGroup>
                            <ControlLabel>{item.label}</ControlLabel>
                            <FormControl
                                type={item.type}
                                name={item.name}
                                value={this.state.vals[item.name]}
                                onChange={this.fieldChangeHandler.bind(this)}
                                />
                            <FormControl.Feedback />
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