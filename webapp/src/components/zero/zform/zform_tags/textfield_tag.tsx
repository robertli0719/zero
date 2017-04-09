/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.1 2017-02-22
 */
import * as React from "react"
import { FormControl, FormGroup, ControlLabel, HelpBlock } from "react-bootstrap"
import { makeRandomString } from "../../../../utilities/random-coder"
import { ZFormTag, ZFormTagAttr, registerTagRender } from "../zform_tag"

registerTagRender("textfield", (tagAttr: ZFormTagAttr) => {
    return <TextFieldTag attr={tagAttr} />
})

registerTagRender("password", (tagAttr: ZFormTagAttr) => {
    return <TextFieldTag attr={tagAttr} />
})

registerTagRender("file", (tagAttr: ZFormTagAttr) => {
    return <TextFieldTag attr={tagAttr} />
})

class TextFieldTag extends ZFormTag {

    private controlId = makeRandomString(32)

    onChange(event: React.FormEvent<HTMLInputElement>) {
        const key = event.currentTarget.name
        const value = event.currentTarget.value
        this.props.attr.onChange(key, value)
    }

    onKeyUp(event: KeyboardEvent) {
        const attr = this.props.attr
        if (attr.schema.enterSubmit && event.keyCode == 13 && attr.onAction) {
            attr.onAction("submit")
        }
    }

    checkType(fieldType: string) {
        switch (fieldType) {
            case "textfield":
                return "text"
            default:
                return fieldType
        }
    }

    render() {
        const attr = this.props.attr
        const error = (attr.error && attr.error.errors.length > 0) ? attr.error.errors : null
        const validateState = error ? "error" : null
        const tag = attr.schema
        const type = this.checkType(tag.type)
        const value = attr.value ? attr.value.toString() : ""
        return (
            <FormGroup controlId={this.controlId} validationState={validateState}>
                <ControlLabel>{tag.label}</ControlLabel>
                <FormControl type={type}
                    name={tag.name}
                    value={value}
                    onChange={this.onChange.bind(this)}
                    onKeyUp={this.onKeyUp.bind(this)}
                    multiple={tag.multiple}
                />
                <FormControl.Feedback />
                <HelpBlock>{error}</HelpBlock>
            </FormGroup>
        )
    }
}