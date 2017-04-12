/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-04-11
 */
import * as React from "react"
import { FormControl, FormGroup, ControlLabel, HelpBlock } from "react-bootstrap"
import { makeRandomString } from "../../../../utilities/random-coder"
import { ZFormTag, ZFormTagAttr, registerTagRender } from "../zform_tag"

registerTagRender("constant", (tagAttr: ZFormTagAttr) => {
    return <HiddenTag attr={tagAttr} />
})

class HiddenTag extends ZFormTag {

    private controlId = makeRandomString(32)

    render() {
        const attr = this.props.attr
        const schema = attr.schema
        const error = (attr.error && attr.error.errors.length > 0) ? attr.error.errors : null
        const value: string = attr.value ? attr.value.toString() : null
        const validateState = error ? "error" : null
        return (
            <FormGroup controlId={this.controlId} validationState={validateState}>
                <ControlLabel>{schema.label}</ControlLabel>
                <FormControl type="textfield"
                    name={schema.name}
                    value={value}
                    disabled={true}
                />
                <FormControl.Feedback />
                <HelpBlock>{error}</HelpBlock>
            </FormGroup>
        )
    }
}