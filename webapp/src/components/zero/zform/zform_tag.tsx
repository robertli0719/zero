/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * This file is the interface for making Input Components.
 * The developer who codes the implementation of a UI component 
 * can implements ZFormTag to make a component, and invoke
 * registerTagRender() to set the new component to zform.
 * 
 * version 1.1.2 2017-02-21
 */
import * as React from "react";
import { TagSchema, FormValue, TagTypeOption, FormError } from "./zform_schema"

export type ZFormTagAttr = {
    schema: TagSchema
    value: FormValue
    error: FormError
    onChange: (key: string, value: any) => void
    onAction: (name: string) => void
}

export type ZFormTagProps = {
    attr: ZFormTagAttr
}

export abstract class ZFormTag extends React.Component<ZFormTagProps, {}> {
    constructor(props: ZFormTagProps) {
        super(props)
    }
}

type TagRender = (ts: ZFormTagAttr) => JSX.Element
const tagRenderMap: { [key: string]: TagRender } = {}

export function registerTagRender(option: TagTypeOption, tagRender: TagRender) {
    if (tagRenderMap[option]) {
        throw "The tag render option " + option + " is duplicate."
    }
    tagRenderMap[option] = tagRender
}

export function showTag(tagAttr: ZFormTagAttr): JSX.Element {
    const ts = tagAttr.schema
    const tagView = tagRenderMap[ts.type]
    if (tagView == null) {
        throw "no tag type support for " + ts.type
    }
    return tagView(tagAttr)
}

export const nopicUrl = "https://raw.githubusercontent.com/robertli0719/ZeroSSH/master/src/main/webapp/img/common/nopic.jpg"