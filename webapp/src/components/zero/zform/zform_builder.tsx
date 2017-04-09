/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.2 2017-02-21
 */
import * as React from "react"
import * as _ from "lodash"
import { TagSchema, FieldPlaceOption, Selection, FormError } from "./zform_schema"

function buildSchema(schema: TagSchema, node: React.ReactNode) {
    React.Children.map(node,
        (child: any) => {
            const type = child.type.name.toLowerCase()
            const tag: TagSchema = _.assign({ type: type }, child.props)
            _.unset(tag, "children")
            if (child.props.children) {
                tag.children = []
                buildSchema(tag, child.props.children)
            }
            schema.children.push(tag)
        }
    )
}

export function buildTagSchema(children: React.ReactNode): TagSchema {
    const root: TagSchema = { type: "map", children: [] }
    buildSchema(root, children)
    return root
}

function getSubTagSchemaByName(schema: TagSchema, name: string) {
    for (const tag of schema.children) {
        if (tag.name == name) {
            return tag
        }
    }
    return null
}

function getSubTagSchemaByKeyArray(schema: TagSchema, keyArray: string[]): TagSchema {
    if (keyArray.length == 0) {
        return null
    }
    return getSubTagSchemaByName(schema, keyArray[0])
}

function appenedError(error: FormError, schema: TagSchema, type: string, message: string, keyArray: string[]) {
    const subSchema = getSubTagSchemaByKeyArray(schema, keyArray)
    if (subSchema != null) {
        const inputName = keyArray[0]
        if (!error.children[inputName]) {
            error.children[inputName] = { errors: [], children: {} }
        }
        appenedError(error.children[inputName], subSchema, type, message, keyArray.slice(1))
        return
    }
    const errorMsg = (keyArray.length > 0 ? keyArray : "") + message
    error.errors.push(errorMsg)
}

export function buildErrorMap(errorDto: any, schema: TagSchema): FormError {
    const error: FormError = { errors: [], children: {} }
    const errorList = errorDto.errors
    for (const item of errorList) {
        const type = item.type
        const message = item.message
        const keyArray = item.source ? item.source.split(".") : []
        appenedError(error, schema, type, message, keyArray)
    }
    return error
}
