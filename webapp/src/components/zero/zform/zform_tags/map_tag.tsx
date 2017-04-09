/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.0 2017-02-21
 */
import * as _ from "lodash"
import * as React from "react"
import { Alert } from "react-bootstrap"
import { ZFormTagProps, ZFormTag, ZFormTagAttr, registerTagRender, showTag } from "../zform_tag"
import { TagSchema } from "../zform_schema"

registerTagRender("map", (tagAttr: ZFormTagAttr) => {
    return <MapTag attr={tagAttr} />
})

class MapTag extends ZFormTag {

    constructor(props: ZFormTagProps) {
        super(props)
        const attr = this.props.attr
        const tag = attr.schema
        if (tag.type != 'map') {
            console.error(tag)
            throw "MapTag get a wrong schema which is not an object tag."
        } else if (!props.attr.value) {
            throw "MapTag doesn't get value in props."
        }
    }

    makeTagAttr(ts: TagSchema): ZFormTagAttr {
        const attr = this.props.attr
        const valMap: any = attr.value
        const value: any = ts.name ? valMap[ts.name] : null
        const error = (ts.name && attr.error && attr.error.children) ? attr.error.children[ts.name] : null
        const togAttr: ZFormTagAttr = {
            schema: ts, value: value, error: error,
            onChange: this.onChange.bind(this),
            onAction: attr.onAction.bind(this)
        }
        return togAttr
    }

    onChange(key: string, value: string) {
        const attr = this.props.attr
        const val: any = _.assign({}, attr.value)
        val[key] = value
        attr.onChange(attr.schema.name, val)
    }

    showErrors() {
        const attr = this.props.attr
        if (!attr.error || attr.error.errors.length == 0) {
            return
        }
        const errors = attr.error.errors
        return errors.map((msg) => {
            return <Alert bsStyle="danger"><strong>Error!</strong> {msg}</Alert>
        })
    }

    render() {
        const tag = this.props.attr.schema
        return (
            <div>
                {this.showErrors()}
                <h3>{tag.label}</h3>
                {
                    tag.children.map((child) => {
                        const tagAttr = this.makeTagAttr(child)
                        return showTag(tagAttr)
                    })
                }
            </div>
        )
    }
}