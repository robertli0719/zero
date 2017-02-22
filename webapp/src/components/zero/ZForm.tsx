/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.8 2017-01-16
 */
import * as React from "react";
import * as ReactDOM from "react-dom";
import * as _ from "lodash"
import { Alert, Button, ButtonToolbar, FormControl, FormGroup, ControlLabel, Checkbox, HelpBlock } from "react-bootstrap";
import * as rb from "react-bootstrap"
import { LinkContainer } from 'react-router-bootstrap';
import { makeRandomString } from "../../utilities/random-coder"
import { ImageUploadButton, UploadOption } from "./ImageUploadButton"
import { http, RestErrorDto, RestErrorItemDto } from "../../utilities/http"

export type FieldPlaceOption = 'default' | 'path' | 'pathAndDto';

export type FieldProps = {
    label: string
    name: string
    value?: string
    place?: FieldPlaceOption
    notNull?: boolean
    multiple?: boolean
    enterSubmit?: boolean
    onSubmit?: () => {}
    errorMap?: { [key: string]: any }
    onChange?: React.FormEventHandler<FormControl>
    onFormChange?: (key: string, val: any) => {}
    valMap?: { [key: string]: any }
}

export type ImageFieldProps = {
    label: string
    name: string
    value?: string
    option?: UploadOption
    errorMap?: { [key: string]: any }
    onFormChange?: (key: string, val: any) => {}
    valMap?: { [key: string]: any }
}

export type TextMapProps = {
    name: string
    value?: { [key: string]: any }
    errorMap?: { [key: string]: any }
    keyList: string[]
    onFormChange?: (key: string, val: any) => {}
    valMap?: { [key: string]: any }
}

export type HiddenFieldProps = {
    name: string
    value: string
    place?: FieldPlaceOption
    notNull?: boolean
}

export type SelectFieldProps = FieldProps & {
    options: { [value: string]: any }
}

export type SubmitProps = {
    onSubmit?: () => {}
    value?: string
    block?: boolean
    bsStyle?: string

}

export type FormProps = {
    action?: string
    method?: string
    successMessage?: string
    onSuccess?: (data: any) => Promise<never>
    onSubmit?: (data: any) => Promise<never>
}

export type FormState = {
    valMap: { [key: string]: any }
    errorMap: { [key: string]: any }
    placeMap: { [key: string]: any }
    notNullMap: { [key: string]: any }
    actionErrors: string[]
    successAlertDisplay: boolean
}

export class Form extends React.Component<FormProps, FormState>{

    constructor(props: FormProps) {
        super(props);
        this.state = { valMap: {}, errorMap: {}, placeMap: {}, notNullMap: {}, actionErrors: [], successAlertDisplay: false }
    }

    onFormChange(key: string, val: any) {
        this.state.valMap[key] = val;
        this.setState(this.state);
    }

    inputChange(event: React.FormEvent<HTMLInputElement>) {
        const key = event.currentTarget.name;
        const type = event.currentTarget.type;
        let val = (type == "checkbox") ? event.currentTarget.checked : event.currentTarget.value;
        this.onFormChange(key, val)
    }

    getSumbitMethod() {
        let method = "POST";
        if (this.props.method) {
            method = this.props.method;
        }
        return method.toUpperCase();
    }

    getUrl() {
        if (!this.props.action) {
            throw "there is no action for this Form"
        } else if (this.props.action.indexOf('{') < 0) {
            return this.props.action;
        }
        let path = this.props.action
        for (const key in this.state.placeMap) {
            const val = this.state.placeMap[key]
            if (val == "default") {
                continue
            }
            const searchString = "{" + encodeURIComponent(key) + "}"
            const variable = encodeURIComponent(this.state.valMap[key])
            path = path.replace(new RegExp(searchString, 'g'), variable)
        }
        if (path.indexOf('{') > 0) {
            throw "ZForm can't match all path variable. please make sure you have set place='path' for action " + this.props.action
        }
        return path;
    }

    getParamUrl() {
        let n = 0;
        let url = this.getUrl();
        for (let key in this.state.valMap) {
            if (this.state.placeMap[key] != "default") {
                continue
            }
            let val = this.state.valMap[key];
            url += (n == 0) ? '?' : '&';
            url += encodeURIComponent(key) + "=" + encodeURIComponent(val);
            n++;
        }
        return url;
    }

    getDto() {
        let dto: { [key: string]: any } = {}
        for (let key in this.state.valMap) {
            if (this.state.placeMap[key] == "path") {
                continue
            }
            dto[key] = this.state.valMap[key];
        }
        return dto;
    }

    submit(): Promise<never> {
        const method = this.getSumbitMethod();
        switch (method) {
            case "GET":
                return http.get(this.getParamUrl());
            case "POST":
                return http.post(this.getUrl(), this.getDto());
            case "PUT":
                return http.put(this.getUrl(), this.getDto());
            case "DELETE":
                return http.delete(this.getParamUrl());
        }
    }

    afterSubmit() {
        this.setState({ errorMap: {}, valMap: {}, actionErrors: [] })
    }

    onSuccess(dto: any) {
        if (this.props.successMessage) {
            this.setState({ successAlertDisplay: true })
        }
        if (!this.props.onSuccess) {
            return;
        }
        let promise = this.props.onSuccess(dto);
        if (promise instanceof Promise == false) {
            promise = new Promise((resolve, reject) => { resolve(); });
        }
        promise.then(() => {
            this.afterSubmit();
        });
    }

    onError(restError: RestErrorDto) {
        for (let id in restError.errors) {
            let item: RestErrorItemDto = restError.errors[id];
            switch (item.type) {
                case "FIELD_ERROR":
                    if (this.state.valMap[item.source] !== undefined) {
                        this.state.errorMap[item.source] = item.message;
                    } else {
                        this.state.actionErrors.push(item.source + ":" + item.message);
                    }
                    this.setState(this.state);
                    break;
                default:
                    this.state.actionErrors.push(item.message);
                    this.setState(this.state);
            }
        }
    }

    validateForm() {
        for (let key in this.state.notNullMap) {
            if (this.state.notNullMap[key] == false) {
                continue
            }
            if (this.state.valMap[key] == '') {
                this.state.errorMap[key] = "can't be empty"
                this.state.actionErrors.push("missing info in form");
                this.setState(this.state)
                return false;
            }
        }
        return true;
    }

    onSubmit() {
        this.setState({ errorMap: {}, actionErrors: [], successAlertDisplay: false })
        if (this.validateForm() == false) {
            return;
        }
        if (this.props.onSubmit) {
            this.props.onSubmit(this.state.valMap);
        }
        if (!this.props.action) {
            return;
        }
        if (!(this.props.onSuccess || this.props.successMessage)) {
            throw "ZFrom can't find onSuccess or successMessage when using action";
        }
        this.submit()
            .then((dto: any) => {
                this.onSuccess(dto);
            })
            .catch((restError: RestErrorDto) => {
                this.onError(restError);
            })
    }

    getChildren() {
        return React.Children.map(this.props.children,
            (child: any) => {
                let key = child.props.name;
                if (key && !this.state.valMap[key]) {
                    let initValue = child.props.value ? child.props.value : "";
                    this.state.valMap[key] = initValue;
                }
                if (key && child.props.notNull) {
                    this.state.notNullMap[key] = true;
                }
                this.state.placeMap[key] = child.props.place ? child.props.place : "default";
                return React.cloneElement(child, {
                    onFormChange: this.onFormChange.bind(this),
                    onChange: this.inputChange.bind(this),
                    valMap: this.state.valMap,
                    errorMap: this.state.errorMap,
                    onSubmit: this.onSubmit.bind(this)
                })
            }
        )
    }

    handleAlertDismiss() {
        this.setState({ successAlertDisplay: false })
    }

    makeSuccessAlert() {
        if (this.state.successAlertDisplay) {
            return <Alert bsStyle="success" onDismiss={this.handleAlertDismiss.bind(this)}>
                <strong>Success!</strong> {this.props.successMessage}
            </Alert>
        }
        return <div></div>
    }

    render() {
        const children = this.getChildren();
        return (
            <div>
                {this.makeSuccessAlert()}
                {
                    this.state.actionErrors.map((msg) => {
                        return <Alert bsStyle="danger"><strong>Error!</strong> {msg}</Alert>
                    })
                }
                {children}
            </div>
        )
    }
}

export class TextField extends React.Component<FieldProps, {}>{

    onKeyUp(event: KeyboardEvent) {
        if (this.props.enterSubmit && event.keyCode == 13) {
            this.props.onSubmit();
        }
    }

    render() {
        const error = this.props.errorMap[this.props.name];
        const validateState = error ? "error" : null;
        return (
            <FormGroup validationState={validateState}>
                <ControlLabel>{this.props.label}</ControlLabel>
                <FormControl type="text"
                    name={this.props.name}
                    value={this.props.valMap[this.props.name]}
                    onChange={this.props.onChange}
                    onKeyUp={this.onKeyUp.bind(this)}
                />
                <FormControl.Feedback />
                <HelpBlock>{error}</HelpBlock>
            </FormGroup>
        )
    }
}

export class Password extends React.Component<FieldProps, {}>{

    onKeyUp(event: KeyboardEvent) {
        if (this.props.enterSubmit && event.keyCode == 13) {
            this.props.onSubmit();
        }
    }

    render() {
        const error = this.props.errorMap[this.props.name];
        const validateState = error ? "error" : null;
        return (
            <FormGroup validationState={validateState}>
                <ControlLabel>{this.props.label}</ControlLabel>
                <FormControl type="password"
                    name={this.props.name}
                    value={this.props.valMap[this.props.name]}
                    onChange={this.props.onChange}
                    onKeyUp={this.onKeyUp.bind(this)}
                />
                <FormControl.Feedback />
                <HelpBlock>{error}</HelpBlock>
            </FormGroup>
        )
    }
}

export class Textarea extends React.Component<FieldProps, {}>{

    private controlId = makeRandomString(32);

    render() {
        const error = this.props.errorMap[this.props.name];
        const validateState = error ? "error" : null;
        return (
            <FormGroup controlId={this.controlId} validationState={validateState}>
                <ControlLabel>{this.props.label}</ControlLabel>
                <FormControl componentClass="textarea" placeholder="textarea"
                    name={this.props.name}
                    value={this.props.valMap[this.props.name]}
                    onChange={this.props.onChange}
                />
                <HelpBlock>{error}</HelpBlock>
            </FormGroup>
        )
    }
}

export class File extends React.Component<FieldProps, {}>{
    render() {
        const error = this.props.errorMap[this.props.name];
        const validateState = error ? "error" : null;
        return (
            <FormGroup validationState={validateState}>
                <ControlLabel>{this.props.label}</ControlLabel>
                <FormControl type="file"
                    name={this.props.name}
                    value={this.props.valMap[this.props.name]}
                    onChange={this.props.onChange}
                    multiple={this.props.multiple}
                />
                <FormControl.Feedback />
                <HelpBlock>{error}</HelpBlock>
            </FormGroup>
        )
    }
}

export class CheckBox extends React.Component<FieldProps, {}>{
    render() {
        return (
            <Checkbox
                name={this.props.name}
                checked={this.props.valMap[this.props.name]}
                onChange={this.props.onChange}
            >
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
                checked={this.props.valMap[this.props.name] == this.props.value}
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
        const error = this.props.errorMap[this.props.name];
        const validateState = error ? "error" : null;
        const options = this.props.options ? this.props.options : []
        if (this.props.options == null) {
            console.warn("Zform.Select.options is null now.")
        }
        return (
            <FormGroup validationState={validateState} controlId={this.controlId}>
                <ControlLabel>{this.props.label}</ControlLabel>
                <FormControl componentClass="select"
                    name={this.props.name}
                    onChange={this.props.onChange}
                    multiple={this.props.multiple}
                    value={this.props.valMap[this.props.name]}
                >
                    <option value="">
                        <span>-- please select --</span>
                    </option>
                    {
                        Object.keys(options).map((value) => {
                            var label = options[value];
                            return <option value={value}>{label}</option>
                        })
                    }
                </FormControl>
                <HelpBlock>{error}</HelpBlock>
            </FormGroup>
        )
    }
}

export type ImageShowerProps = {
    name: string
    valMap?: { [key: string]: any }
    style?: React.CSSProperties;
}

export class ImageShower extends React.Component<ImageShowerProps, {}>{

    render() {
        return <img src={this.props.valMap[this.props.name]} style={this.props.style} />
    }
}

export class Image extends React.Component<ImageFieldProps, {}>{

    constructor(props: ImageFieldProps) {
        super(props)
        this.state = {}
    }

    onSuccess(url: string) {
        this.props.onFormChange(this.props.name, url)
    }

    render() {
        const error = this.props.errorMap[this.props.name];
        const validateState = error ? "error" : null;
        return (
            <FormGroup validationState={validateState}>
                <ControlLabel>{this.props.label}</ControlLabel>
                <input
                    name={this.props.name}
                    value={this.props.valMap[this.props.name]}
                    type="hidden" />
                <ImageUploadButton option={this.props.option} onSuccess={this.onSuccess.bind(this)}>
                    <span>{this.props.label}</span>
                    <img src={this.props.valMap[this.props.name]} style={{ width: "26px", "maxHeight": "26px", padding: "3px" }} />
                </ImageUploadButton>
                <FormControl.Feedback />
                <HelpBlock>{error}</HelpBlock>
            </FormGroup>
        )
    }
}

export class TextMap extends React.Component<TextMapProps, {}>{

    constructor(props: TextMapProps) {
        super(props)
        this.props.onFormChange(this.props.name, this.getValMap())
    }

    makeSubInputName(name: string) {
        return this.props.name + "[" + name + "]"
    }

    getValMap() {
        let valMap = _.assign({}, this.props.valMap[this.props.name]);

        this.props.keyList.map((key) => {
            if (valMap[key] == undefined) {
                valMap[key] = ""
            }
        })
        return valMap;
    }

    onChange(event: React.FormEvent<HTMLInputElement>) {
        const target = event.currentTarget;
        const name = target.name.match(/\[(.*?)\]/)[1]
        const val = target.value

        const mapVal = this.getValMap()
        mapVal[name] = val
        this.props.onFormChange(this.props.name, mapVal)
    }

    makeTextField(label: string, name: string, val: string) {
        return (
            <FormGroup>
                <ControlLabel>{label}</ControlLabel>
                <FormControl type="text"
                    name={name}
                    value={val}
                    onChange={this.onChange.bind(this)}
                />
                <FormControl.Feedback />
            </FormGroup>
        )
    }

    render() {
        const keyList = this.props.keyList ? this.props.keyList : []
        if (this.props.keyList == null) {
            console.warn("ZForm.TextMap.keyList is null")
        }
        const error = this.props.errorMap[this.props.name];
        const validateState = error ? "error" : null;

        return (
            <div>
                {
                    keyList.map((key) => {
                        const label: string = key
                        const name: string = this.makeSubInputName(key)
                        let value: string = this.getValMap()[key]
                        return this.makeTextField(label, name, value)
                    })
                }
            </div>
        )
    }
}

export class Submit extends React.Component<SubmitProps, {}>{

    onSubmit() {
        this.props.onSubmit();
    }

    render() {
        const label = this.props.value ? this.props.value : "Submit";
        return <Button type="submit" onClick={this.onSubmit.bind(this)} bsStyle={this.props.bsStyle} block={this.props.block}>{label}</Button>
    }
}

export function dtoListToOptions(dtoList: Array<any>, keyName = "name", labelName = keyName) {
    let options: { [key: string]: any } = {}
    for (const id in dtoList) {
        const dto = dtoList[id];
        const value = dto[keyName];
        const label = dto[labelName];
        options[value] = label;
    }
    return options;
}

export function fetchSelectOptions(url: string, keyName = "name", labelName = keyName) {
    return http.get(url)
        .then((dtoList: any) => {
            return dtoListToOptions(dtoList, keyName, labelName);
        }).catch((error: RestErrorDto) => {
            console.log("Error:", error);
            throw error;
        });
}