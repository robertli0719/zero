/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-05-03
 */

import * as React from "react"
import { FormControl, FormGroup, ControlLabel, HelpBlock, ButtonToolbar, Button } from "react-bootstrap"
import { makeRandomString } from "../../../../utilities/random-coder"
import { ZFormTag, ZFormTagAttr, registerTagRender } from "../zform_tag"

registerTagRender("textlist", (tagAttr: ZFormTagAttr) => {
    return <TextListTag attr={tagAttr} />
})

class TextListTag extends ZFormTag {

    private controlId = makeRandomString(32)

    onChange(index: number, event: React.FormEvent<HTMLInputElement>) {
        const value: string = event.currentTarget.value
        const modalValue = this.props.attr.value as string[]
        modalValue[index] = value
        const key = this.props.attr.schema.name
        this.props.attr.onChange(key, modalValue)
    }

    moreItem() {
        const attrValue = this.props.attr.value as string[]
        const key = this.props.attr.schema.name
        const modalValue = attrValue ? attrValue : []
        modalValue.push("")
        this.props.attr.onChange(key, modalValue)
    }

    lessItem() {
        const key = this.props.attr.schema.name
        const modalValue = this.props.attr.value as string[]
        modalValue.splice(-1, 1)
        this.props.attr.onChange(key, modalValue)
    }

    renderTextField(val: string, index: number) {
        const label = this.props.attr.schema.label + " " + (index + 1)
        const tagName = this.props.attr.schema.name + "_" + index
        return (
            <FormGroup controlId={this.controlId}>
                <ControlLabel>{label}</ControlLabel>
                <FormControl type="text"
                    name={tagName}
                    value={val}
                    onChange={this.onChange.bind(this, index)}
                />
                <FormControl.Feedback />
            </FormGroup>
        )
    }

    render() {
        const attr = this.props.attr
        const error = (attr.error && attr.error.errors.length > 0) ? attr.error.errors : null
        const validateState = error ? "error" : null
        const value: string[] = attr.value instanceof Array ? attr.value as string[] : [] as string[]
        const label: string = this.props.attr.schema.label
        return (
            <div>
                <hr />
                {value.map((val: string, index: number) => {
                    return this.renderTextField(val, index)
                })}
                <HelpBlock>{error}</HelpBlock>
                <ButtonToolbar>
                    <Button bsSize="xs" bsStyle="success" onClick={this.moreItem.bind(this)}>more {label}</Button>
                    <Button bsSize="xs" bsStyle="danger" onClick={this.lessItem.bind(this)} disabled={value.length == 0} >less {label}</Button>
                </ButtonToolbar>
                <hr />
            </div>
        )
    }
}