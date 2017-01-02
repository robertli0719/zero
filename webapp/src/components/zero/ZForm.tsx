/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.1 2017-01-01
 */
import * as React from "react";
import * as ReactDOM from "react-dom";
import { Button, ButtonToolbar, FormControl, FormGroup, ControlLabel, Checkbox } from "react-bootstrap";
import * as rb from "react-bootstrap"
import { LinkContainer } from 'react-router-bootstrap';
import { makeRandomString } from "../../utilities/random-coder"
import { http } from "../../utilities/http"

export type FieldProps = {
    label: string
    name: string
    value?: string
    multiple?: boolean
    errorMessage?: string
    onChange?: React.FormEventHandler<FormControl>
    valMap?: { [key: string]: any }
}

export type HiddenFieldProps = {
    name: string
    value: string
}

export type SelectFieldProps = FieldProps & {
    options: { [value: string]: any }
}

export type FormProps = {
    action?: string
    method?: string
    onRespond?: (promise: Promise<any>) => Promise<any>
    onSubmit?: (data: any) => {}
}

export type FormState = {
    valMap: { [key: string]: any }
}

export type SubmitProps = {
    onSubmit?: () => {}
}


export class Form extends React.Component<FormProps, FormState>{

    constructor(props: FormProps) {
        super(props);
        this.state = { valMap: {} }
    }

    inputChange(event: React.FormEvent<HTMLInputElement>) {
        let key = event.currentTarget.name;
        let type = event.currentTarget.type;

        if (type == "checkbox") {
            const checked: boolean = event.currentTarget.checked;
            this.state.valMap[key] = checked;
        } else {
            const val = event.currentTarget.value;
            this.state.valMap[key] = val;
        }
        this.setState(this.state);
    }

    getSumbitMethod() {
        let method = "POST";
        if (this.props.method) {
            method = this.props.method;
        }
        return method.toUpperCase();
    }

    getParamUrl() {
        let n = 0;
        let url = this.props.action;
        for (let key in this.state.valMap) {
            let val = this.state.valMap[key];
            url += (n == 0) ? '?' : '&';
            url += encodeURIComponent(key) + "=" + encodeURIComponent(val);
            n++;
        }
        return url;
    }

    submit(): Promise<never> {
        const method = this.getSumbitMethod();
        switch (method) {
            case "GET":
                return http.get(this.getParamUrl());
            case "POST":
                return http.post(this.props.action, this.state.valMap);
            case "PUT":
                return http.put(this.props.action, this.state.valMap);
            case "DELETE":
                return http.delete(this.getParamUrl());
        }
    }

    afterSubmit() {
        console.log("after promise:");
    }

    onSubmit() {
        if (this.props.onSubmit) {
            this.props.onSubmit(this.state.valMap);
            return;
        }
        if (!this.props.action) {
            return;
        }
        let promise = this.submit();
        if (this.props.onRespond) {
            const newPromise = this.props.onRespond(promise);
            if (newPromise instanceof Promise) {
                promise = newPromise;
            }
        }
        promise.then(() => {
            this.afterSubmit();
        });
    }

    render() {
        const children = React.Children.map(this.props.children,
            (child: any) => {
                let key = child.props.name;
                if (key && !this.state.valMap[key]) {
                    let initValue = child.props.value ? child.props.value : "";
                    this.state.valMap[key] = initValue;
                }
                return React.cloneElement(child, {
                    onChange: this.inputChange.bind(this),
                    valMap: this.state.valMap,
                    onSubmit: this.onSubmit.bind(this)
                })
            }
        );
        return <div>{children}</div>
    }
}

export class TextField extends React.Component<FieldProps, {}>{
    render() {
        return (
            <FormGroup>
                <ControlLabel>{this.props.label}</ControlLabel>
                <FormControl type="text"
                    name={this.props.name}
                    value={this.props.valMap[this.props.name]}
                    onChange={this.props.onChange}
                    />
                <FormControl.Feedback />
                <p>{this.props.errorMessage}</p>
            </FormGroup>
        )
    }
}

export class Password extends React.Component<FieldProps, {}>{
    render() {
        return (
            <FormGroup>
                <ControlLabel>{this.props.label}</ControlLabel>
                <FormControl type="password"
                    name={this.props.name}
                    value={this.props.valMap[this.props.name]}
                    onChange={this.props.onChange}
                    />
                <FormControl.Feedback />
                <p>{this.props.errorMessage}</p>
            </FormGroup>
        )
    }
}

export class Textarea extends React.Component<FieldProps, {}>{

    private controlId = makeRandomString(32);

    render() {
        return (
            <FormGroup controlId={this.controlId}>
                <ControlLabel>{this.props.label}</ControlLabel>
                <FormControl componentClass="textarea" placeholder="textarea"
                    name={this.props.name}
                    value={this.props.valMap[this.props.name]}
                    onChange={this.props.onChange}
                    />
            </FormGroup>
        )
    }
}

export class File extends React.Component<FieldProps, {}>{
    render() {
        return (
            <FormGroup>
                <ControlLabel>{this.props.label}</ControlLabel>
                <FormControl type="file"
                    name={this.props.name}
                    value={this.props.valMap[this.props.name]}
                    onChange={this.props.onChange}
                    multiple={this.props.multiple}
                    />
                <FormControl.Feedback />
                <p>{this.props.errorMessage}</p>
            </FormGroup>
        )
    }
}

export class CheckBox extends React.Component<FieldProps, {}>{
    render() {
        return (
            <Checkbox
                name={this.props.name}
                onChange={this.props.onChange}>
                {this.props.label}
            </Checkbox>
        )
    }
}

export class Radio extends React.Component<FieldProps, {}>{
    render() {
        return (
            <rb.Radio
                name={this.props.name}
                value={this.props.value}
                onChange={this.props.onChange}>
                {this.props.label}
            </rb.Radio>
        )
    }
}

export class Hidden extends React.Component<HiddenFieldProps, {}>{
    render() {
        return (
            <input
                name={this.props.name}
                value={this.props.value}
                type="hidden" />
        )
    }
}

export class Select extends React.Component<SelectFieldProps, {}>{

    private controlId = makeRandomString(32);

    render() {
        return (
            <FormGroup controlId={this.controlId}>
                <ControlLabel>{this.props.label}</ControlLabel>
                <FormControl componentClass="select"
                    name={this.props.name}
                    onChange={this.props.onChange}
                    multiple={this.props.multiple}
                    >
                    <option value="">-- please select --</option>
                    {
                        Object.keys(this.props.options).map((value) => {
                            var label = this.props.options[value];
                            return <option value={value}>{label}</option>
                        })
                    }
                </FormControl>
            </FormGroup>
        )
    }
}

export class Submit extends React.Component<SubmitProps, {}>{

    onSubmit() {
        this.props.onSubmit();
    }

    render() {
        return <Button type="submit" onClick={this.onSubmit.bind(this)}>Submit</Button>
    }
}