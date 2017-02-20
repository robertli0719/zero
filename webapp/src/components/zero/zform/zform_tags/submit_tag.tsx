/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.0 2017-02-19
 */
import * as React from "react";
import { Button } from "react-bootstrap";
import { ZFormTag, ZFormTagAttr, registerTagRender } from "../zform_tag"

registerTagRender("submit", (tagAttr: ZFormTagAttr) => {
    return <SubmitTag attr={tagAttr} />
})


class SubmitTag extends ZFormTag {

    onClick() {
        this.props.attr.onAction("submit")
    }

    render() {
        const schema = this.props.attr.schema
        const label = schema.value ? schema.value : "Submit";
        return <Button type="submit" onClick={this.onClick.bind(this)} bsStyle={schema.bsStyle} block={schema.block}>{label}</Button>
    }
}