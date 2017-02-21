/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.1 2017-02-20
 */
import * as React from "react"
import { Alert } from "react-bootstrap"
import { http, RestErrorDto, RestErrorItemDto } from "../../../utilities/http"
import { TagSchema, buildDefaultValue, ValueMap, FormError, FormValue } from "./zform_schema"
import { ZFormTagAttr, showTag } from "./zform_tag"
import * as builder from "./zform_builder"
import * as submitter from "./zform_submitter"
import { buildUri } from "./zform_uri_builder"
import "./zform_tags/tags_importer"

export * from "./zform_child"
export * from "./zform_utilities"
export type TagSchema = TagSchema

type FormProps = {
    schema?: TagSchema
    value?: FormValue
    error?: FormError
    action?: string
    method?: string
    successMessage?: string
    onSuccess?: (data: any) => Promise<never>
    onSubmit?: (data: any) => Promise<never>
}

type FormState = {
    schema: TagSchema
    value: FormValue
    error: FormError
    successAlertDisplay: boolean
}

export class Form extends React.Component<FormProps, FormState>{

    constructor(props: FormProps) {
        super(props);
        let schema: TagSchema = props.schema;
        if (schema == null) {
            schema = builder.buildTagSchema(this.props.children)
        }
        const value: FormValue = props.value ? props.value : buildDefaultValue(schema)
        const error: FormError = props.error ? props.error : { errors: [], children: {} }
        this.state = { schema: schema, value: value, error: error, successAlertDisplay: false }
    }

    submit(): void {
        const dto: any = this.state.value
        let uri = ""
        try {
            uri = buildUri(this.props.action, this.state.schema, this.state.value)
        } catch (error) {
            console.error(error)
            this.state.error = error
            this.setState(this.state)
            return
        }
        const request: submitter.Request = {
            uri: uri,
            method: this.props.method,
            data: dto
        }
        this.state.error = { errors: [], children: {} }
        this.state.successAlertDisplay = false
        this.setState(this.state)
        new Promise((resolve, reject) => { resolve() })
            .then(() => {
                if (this.props.onSubmit) {
                    return this.props.onSubmit(dto)
                }
            }).then(() => {
                return submitter.submit(request)
            }).then((data: any) => {
                if (this.props.successMessage) {
                    this.state.successAlertDisplay = true;
                    this.setState(this.state)
                }
                if (this.props.onSuccess) {
                    return this.props.onSuccess(data)
                }
            }).catch((errorDto: any) => {
                const error = builder.buildErrorMap(errorDto, this.state.schema)
                this.state.error = error
                this.setState(this.state)
            })
    }

    onAction(name: string, payload: any) {
        switch (name) {
            case "submit":
                this.submit();
                break;
        }
    }

    onChange(key: string, value: any) {
        //key is undefined for zfrom
        this.state.value = value
        this.setState(this.state)
    }

    handleAlertDismiss() {
        this.state.successAlertDisplay = false
        this.setState(this.state)
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
        let attr: ZFormTagAttr = {
            schema: this.state.schema,
            value: this.state.value,
            error: this.state.error,
            onAction: this.onAction.bind(this),
            onChange: this.onChange.bind(this)
        }
        return (
            <div>
                {this.makeSuccessAlert()}
                {showTag(attr)}
            </div>
        )
    }

}
