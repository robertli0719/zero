/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.0 2017-02-19
 */
import * as React from "react";
import { FormControl, FormGroup, ControlLabel, HelpBlock } from "react-bootstrap";
import { makeRandomString } from "../../../../utilities/random-coder"
import { ZFormTag, ZFormTagAttr, registerTagRender } from "../zform_tag"

registerTagRender("select", (tagAttr: ZFormTagAttr) => {
    return <SelectTag attr={tagAttr} />
})


type SelectTagProps = {
    label: string
    name: string
    error: string
    multiple?: boolean
    value: string
    options: { [value: string]: any }
    onChange: (key: string, value: string) => void
}

class SelectTag extends ZFormTag {

    private controlId = makeRandomString(32);

    onChange(event: React.FormEvent<HTMLInputElement>) {
        const key = event.currentTarget.name;
        const value = event.currentTarget.value;
        this.props.attr.onChange(key, value);
    }

    render() {
        const attr = this.props.attr
        const error = attr.error
        const validateState = error ? "error" : null;
        const schema = attr.schema
        const options = attr.schema.selections

        return (
            <FormGroup validationState={validateState} controlId={this.controlId}>
                <ControlLabel>{schema.label}</ControlLabel>
                <FormControl componentClass="select"
                    name={schema.name}
                    onChange={this.onChange.bind(this)}
                    multiple={schema.multiple}
                >
                    <option value="">
                        <span>-- please select --</span>
                    </option>
                    {
                        options.map((option) => {
                            const label = option.label
                            const value = option.value
                            return <option value={value}>{label}</option>
                        })
                    }
                </FormControl>
                <HelpBlock>{error}</HelpBlock>
            </FormGroup>
        )
    }
}