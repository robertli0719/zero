/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.0 2017-02-20
 */

import * as React from "react";
import { FormGroup, FormControl, HelpBlock, Radio, ControlLabel } from "react-bootstrap";
import { ZFormTag, ZFormTagAttr, registerTagRender, nopicUrl } from "../zform_tag"
import { ImageUploadButton, UploadOption } from "../../ImageUploadButton"

registerTagRender("image", (tagAttr: ZFormTagAttr) => {
    return <ImageTag attr={tagAttr} />
})

class ImageTag extends ZFormTag {

    onChange(event: React.FormEvent<HTMLInputElement>) {
        const name = this.props.attr.schema.name
        const value = event.currentTarget.value
        this.props.attr.onChange(name, value)
    }

    onSuccess(imgUrl: string) {
        const name = this.props.attr.schema.name
        this.props.attr.onChange(name, imgUrl)
    }

    pickOption(option: string): UploadOption {
        switch (option) {
            case "default":
                return "default"
            case "cropped":
                return "cropped"
            case "fixed":
                return "fixed"
            default:
                return "default"
        }
    }

    render() {
        const attr = this.props.attr
        const src = attr.value ? attr.value.toString() : nopicUrl
        const error = attr.error
        const validateState = (error && error.errors.length > 0) ? "error" : null
        const option = this.pickOption(attr.schema.option)

        return (
            <FormGroup validationState={validateState}>

                <ImageUploadButton option={option} onSuccess={this.onSuccess.bind(this)}>
                    <span>{attr.schema.label}</span>
                    <img src={src} style={
                        { width: "26px", "maxHeight": "26px", padding: "3px" }}
                    />
                </ImageUploadButton>
                <FormControl.Feedback />
                <HelpBlock>{error}</HelpBlock>
            </FormGroup>
        )
    }
}