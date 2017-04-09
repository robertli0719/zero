/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-04-08
 */

import * as React from "react"
import { FormGroup, FormControl, HelpBlock, Radio, ControlLabel } from "react-bootstrap"
import { ZFormTag, ZFormTagAttr, registerTagRender, nopicUrl } from "../zform_tag"
import { VideoUploadButton } from "../../VideoUploadButton"

registerTagRender("video", (tagAttr: ZFormTagAttr) => {
    return <VideoTag attr={tagAttr} />
})

class VideoTag extends ZFormTag {

    onChange(event: React.FormEvent<HTMLInputElement>) {
        const name = this.props.attr.schema.name
        const value = event.currentTarget.value
        this.props.attr.onChange(name, value)
    }

    onSuccess(imgUrl: string) {
        const name = this.props.attr.schema.name
        this.props.attr.onChange(name, imgUrl)
    }

    render() {
        const attr = this.props.attr
        const src = attr.value ? attr.value.toString() : nopicUrl
        const error = (attr.error && attr.error.errors.length > 0) ? attr.error.errors : null
        const validateState = error ? "error" : null

        return (
            <FormGroup validationState={validateState}>
                <VideoUploadButton onSuccess={this.onSuccess.bind(this)}>
                    <span>{attr.schema.label} </span>
                    <span className="glyphicon glyphicon-film"></span>
                </VideoUploadButton>
                <FormControl.Feedback />
                <HelpBlock>{error}</HelpBlock>
            </FormGroup>
        )
    }
}

