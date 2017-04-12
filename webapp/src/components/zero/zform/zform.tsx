/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.7 2017-04-11
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
        super(props)
        let schema: TagSchema = props.schema
        if (schema == null) {
            schema = builder.buildTagSchema(this.props.children)
        }
        const value: FormValue = props.value ? props.value : buildDefaultValue(schema)
        const error: FormError = props.error ? props.error : { errors: [], children: {} }
        this.state = { schema: schema, value: value, error: error, successAlertDisplay: false }
    }

    componentWillReceiveProps(nextProps: any, nextContext: any): void {
        let schema: TagSchema = nextProps.schema
        if (schema == null) {
            schema = builder.buildTagSchema(nextProps.children)
        }
        const value: FormValue = nextProps.value ? nextProps.value : buildDefaultValue(schema)
        this.setState({ schema: schema, value: value })
    }

    submit(): void {
        const dto: any = this.state.value
        let uri = ""
        try {
            uri = buildUri(this.props.action, this.state.schema, this.state.value)
        } catch (error) {
            console.error(error)
            this.setState({ error: error })
            return
        }
        const request: submitter.Request = {
            uri: uri,
            method: this.props.method,
            data: dto
        }
        const error: FormError = { errors: [], children: {} }
        this.setState({ error: error, successAlertDisplay: false })
        new Promise((resolve, reject) => { resolve() })
            .then(() => {
                if (this.props.onSubmit) {
                    return this.props.onSubmit(dto)
                }
            }).then(() => {
                return submitter.submit(request)
            }).then((data: any) => {
                if (this.props.successMessage) {
                    this.setState({ successAlertDisplay: true })
                }
                if (this.props.onSuccess) {
                    return this.props.onSuccess(data)
                }
            }).catch((errorDto: any) => {
                const error = builder.buildErrorMap(errorDto, this.state.schema)
                this.setState({ error: error })
            })
    }

    onAction(name: string, payload: any) {
        switch (name) {
            case "submit":
                this.submit()
                break
        }
    }

    onChange(key: string, value: any) {
        //key is undefined for zfrom
        this.setState({ value: value })
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
        const attr: ZFormTagAttr = {
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
