/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.1 2017-02-28
 */
import * as React from "react";
import { ZFormTag, ZFormTagAttr, registerTagRender } from "../zform_tag"

registerTagRender("hidden", (tagAttr: ZFormTagAttr) => {
    return <HiddenTag attr={tagAttr} />
})

class HiddenTag extends ZFormTag {
    render() {
        let val = this.props.attr.value
        const name = this.props.attr.schema.name
        const value: string = val ? val.toString() : null
        return (
            <input
                name={name}
                value={value}
                type="hidden" />
        )
    }
}