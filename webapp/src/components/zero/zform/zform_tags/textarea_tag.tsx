/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.1 2017-03-03
 */
import * as React from "react"
import { FormControl, FormGroup, ControlLabel, HelpBlock } from "react-bootstrap"
import { makeRandomString } from "../../../../utilities/random-coder"
import { ZFormTag, ZFormTagAttr, registerTagRender } from "../zform_tag"

registerTagRender("textarea", (tagAttr: ZFormTagAttr) => {
    return <TextAreaTag attr={tagAttr} />
})

class TextAreaTag extends ZFormTag {

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

    render() {
        const attr = this.props.attr
        const error = (attr.error && attr.error.errors.length > 0) ? attr.error.errors : null
        const validateState = error ? "error" : null
        const tag = attr.schema
        const value = attr.value ? attr.value.toString() : null
        return (
            <FormGroup controlId={this.controlId} validationState={validateState}>
                <ControlLabel>{tag.label}</ControlLabel>
                <FormControl componentClass="textarea" placeholder="textarea"
                    name={tag.name}
                    value={value}
                    onChange={this.onChange.bind(this)}
                    onKeyUp={this.onKeyUp.bind(this)}
                />
                <FormControl.Feedback />
                <HelpBlock>{error}</HelpBlock>
            </FormGroup>
        )
    }
}