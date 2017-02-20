/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.0 2017-02-19
 */
import * as React from "react";
import { ZFormTag, ZFormTagAttr, registerTagRender } from "../zform_tag"

registerTagRender("hidden", (tagAttr: ZFormTagAttr) => {
    return <HiddenTag attr={tagAttr} />
})

class HiddenTag extends ZFormTag {
    render() {
        const name = this.props.attr.schema.name
        const value = this.props.attr.value
        return (
            <input
                name={name}
                value={value.toString()}
                type="hidden" />
        )
    }
}