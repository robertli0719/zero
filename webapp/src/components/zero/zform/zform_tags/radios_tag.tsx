/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.0 2017-02-19
 */
import * as React from "react";
import { Radio, ControlLabel } from "react-bootstrap";
import { ZFormTag, ZFormTagAttr, registerTagRender } from "../zform_tag"

registerTagRender("radios", (tagAttr: ZFormTagAttr) => {
    return <RadioGroupTag attr={tagAttr} />
})

class RadioGroupTag extends ZFormTag {

    onChange(event: React.FormEvent<HTMLInputElement>) {
        const name = this.props.attr.schema.name
        const value = event.currentTarget.value
        this.props.attr.onChange(name, value)
        console.log("RadioGroupTag onChange", name, value)
    }

    showLabel() {
        const label = this.props.attr.schema.label
        if (label) {
            return <ControlLabel>{label}</ControlLabel>
        }
    }

    render() {
        const attr = this.props.attr
        return (
            <div>
                {this.showLabel()}
                {attr.schema.selections.map((selection) => {
                    return <Radio
                        name={attr.schema.name}
                        checked={selection.value == attr.value}
                        value={selection.value}
                        onChange={this.onChange.bind(this)}>
                        {selection.label}
                    </Radio>
                })}
            </div>
        )
    }
}
