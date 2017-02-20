/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.0 2017-02-19
 */
import * as React from "react";
import { Checkbox } from "react-bootstrap"
import { makeRandomString } from "../../../../utilities/random-coder"
import { ZFormTagProps, ZFormTag, ZFormTagAttr, registerTagRender } from "../zform_tag"

registerTagRender("checkbox", (tagAttr: ZFormTagAttr) => {
    return <CheckBoxTag attr={tagAttr} />
})

class CheckBoxTag extends ZFormTag {

    onChange(event: React.FormEvent<HTMLInputElement>) {
        const key = event.currentTarget.name;
        const value = event.currentTarget.checked;
        this.props.attr.onChange(key, value);
    }

    render() {
        const attr = this.props.attr
        const tag = attr.schema
        const value = attr.value
        return (
            <Checkbox
                name={tag.name}
                checked={value == true}
                onChange={this.onChange.bind(this)}
            >
                {tag.label}
            </Checkbox>
        )
    }
}