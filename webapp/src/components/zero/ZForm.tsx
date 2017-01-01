/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0 2017-01-01
 */
import * as React from "react";
import * as ReactDOM from "react-dom";
import { Button, ButtonToolbar, FormControl, FormGroup, ControlLabel, Checkbox } from "react-bootstrap";
import * as rb from "react-bootstrap"
import { LinkContainer } from 'react-router-bootstrap';

export type FieldProps = {
    label: string
    name: string
    value?: string
    multiple?: boolean
    errorMessage?: string
    onChange?: React.FormEventHandler<FormControl>;
    valMap?: { [key: string]: any }
}

export type FormProps = {
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

    onSubmit() {
        this.props.onSubmit(this.state.valMap);
    }

    render() {
        const children = React.Children.map(this.props.children,
            (child: any) => {
                let key = child.props.name;
                if (key && !this.state.valMap[key]) {
                    this.state.valMap[key] = "";
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

export class Submit extends React.Component<SubmitProps, {}>{

    onSubmit() {
        this.props.onSubmit();
    }

    render() {
        return <Button type="submit" onClick={this.onSubmit.bind(this)}>Submit</Button>
    }
}