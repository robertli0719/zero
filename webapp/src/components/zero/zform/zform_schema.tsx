/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.3 2017-04-11
 */
import * as React from "react"

export type FieldPlaceOption = 'default' | 'path' | 'pathAndDto'
export type TagTypeOption = "map" | "textfield" | "password" | "textarea" | "file" | "checkbox" | "checkboxes" | "radios" | "hidden" | "constant" | "select" | "image" | "imageshower" | "images" | "video" | "videoplayer" | "submit"

export type ValueMap = { [key: string]: FormValue }
export type FormError = { errors: string[], children: { [key: string]: any } }
export type FormValue = string | string[] | number | boolean | ValueMap | ValueMap[]

export type Selection = {
    label: string
    value: string
}

export type TagSchema = {
    type: TagTypeOption
    label?: string
    name?: string
    value?: FormValue
    place?: FieldPlaceOption
    enterSubmit?: boolean
    block?: boolean
    bsStyle?: string
    option?: string
    multiple?: boolean
    selections?: Selection[]
    children?: TagSchema[]
}

export function buildDefaultValue(tag: TagSchema): FormValue {
    if (!tag.children) {
        return tag.value
    }
    const object: ValueMap = {}
    for (const child of tag.children) {
        if (!child.name) {
            continue
        }
        object[child.name] = buildDefaultValue(child)
    }
    return object
}
