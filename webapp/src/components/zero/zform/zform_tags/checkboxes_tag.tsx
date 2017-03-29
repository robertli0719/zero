/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-03-24
 */
import * as React from "react"
import { Checkbox, ControlLabel } from "react-bootstrap"
import { ZFormTag, ZFormTagAttr, registerTagRender } from "../zform_tag"

registerTagRender("checkboxes", (tagAttr: ZFormTagAttr) => {
    return <CheckBoxesTag attr={tagAttr} />
})

class CheckBoxesTag extends ZFormTag {

    getValue(): string[] {
        const attr = this.props.attr
        if (attr.value) {
            return attr.value as string[]
        } else if (attr.schema.value) {
            return attr.schema.value as string[]
        }
        return []
    }

    onChange(event: React.FormEvent<HTMLInputElement>) {
        const name = this.props.attr.schema.name
        const checkboxVal = event.currentTarget.value.toString()
        const value = this.getValue()

        if (value.indexOf(checkboxVal) >= 0) {
            value.splice(value.indexOf(checkboxVal), 1)
        } else {
            value.push(checkboxVal)
        }
        value.sort()
        this.props.attr.onChange(name, value)
    }

    showLabel() {
        const label = this.props.attr.schema.label
        if (label) {
            return <ControlLabel>{label}</ControlLabel>
        }
    }

    render() {
        const attr = this.props.attr
        const attrVal = attr.value as string[]
        const value = this.getValue()

        return (
            <div>
                {this.showLabel()}
                {attr.schema.selections.map((selection) => {
                    const selectionVal = selection.value.toString()
                    const checked = value.indexOf(selectionVal) >= 0
                    return <Checkbox
                        name={attr.schema.name}
                        checked={checked}
                        value={selectionVal}
                        onChange={this.onChange.bind(this)}>
                        {selection.label}
                    </Checkbox>
                })}
            </div>
        )
    }
}
