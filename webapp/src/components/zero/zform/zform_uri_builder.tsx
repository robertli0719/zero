/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * warning: can't support nested value in path.
 * warning: FieldPlaceOption.path == FieldPlaceOption.pathAndDto
 * 
 * version 1.1.0 2017-02-19
 */
import { TagSchema, FormError, FormValue, ValueMap } from "./zform_schema"

export function buildUri(action: string, schema: TagSchema, data: FormValue): string {
    if (!action) {
        return action
    } else if (action.indexOf('{') < 0) {
        return action
    } else if (schema.type != 'object') {
        return action
    }

    for (schema of schema.children) {
        if (!schema.place || schema.place == "default") {
            continue
        }
        const key = schema.name
        const dataMap = data as ValueMap

        if (!dataMap[key] || dataMap[key].toString().trim().length == 0) {
            const errorChildren = {} as { [key: string]: any }
            errorChildren[key] = { errors: ["may not be empty"], children: [] }
            throw { errors: [], children: errorChildren } as FormError
        }
        const searchString = "{" + encodeURIComponent(key) + "}"
        const variable = encodeURIComponent(dataMap[key].toString())
        action = action.replace(new RegExp(searchString, 'g'), variable)
    }

    if (action.indexOf('{') > 0) {
        throw "zform can't match all path variable. please make sure you have set place='path' for action " + this.props.action
    }
    return action
}