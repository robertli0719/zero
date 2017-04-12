/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-04-11
 */

import * as React from "react"
import { FormGroup, FormControl, HelpBlock, Radio, ControlLabel } from "react-bootstrap"
import { ZFormTag, ZFormTagAttr, registerTagRender, nopicUrl } from "../zform_tag"
import { ImagesUploadButton } from "../../ImagesUploadButton"
import { IF } from "../../stl/IF"

registerTagRender("images", (tagAttr: ZFormTagAttr) => {
    return <ImagesTag attr={tagAttr} />
})

class ImagesTag extends ZFormTag {

    onSuccess(urlArray: string[]) {
        const name = this.props.attr.schema.name
        this.props.attr.onChange(name, urlArray)
    }

    render() {
        const attr = this.props.attr
        const value = attr.value as string[]
        const src = value && value.length > 0 ? value[0].toString() : nopicUrl
        const error = (attr.error && attr.error.errors.length > 0) ? attr.error.errors : null
        const validateState = error ? "error" : null
        const number = value ? value.length : 0

        return (
            <FormGroup validationState={validateState}>
                <ImagesUploadButton onSuccess={this.onSuccess.bind(this)}>
                    <span>{attr.schema.label}</span>
                    <img src={src} style={
                        { width: "26px", "maxHeight": "26px", padding: "3px" }}
                    />
                    <IF test={number > 0}>
                        <span> * {number}</span>
                    </IF>
                </ImagesUploadButton>
                <FormControl.Feedback />
                <HelpBlock>{error}</HelpBlock>
            </FormGroup>
        )
    }
}