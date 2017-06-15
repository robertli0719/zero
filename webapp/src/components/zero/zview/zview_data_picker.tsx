/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.1 2017-06-14
 */
import * as _ from "lodash"
import { DataType } from "./zview_schema"

export function pickNames(dtoList: any[], select: string[]) {
    if (select) {
        return select
    }
    const array: Array<any> = dtoList
    return Object.keys(array[0])
}

export function pickHeads(dtoList: any[], select: string[], heads: string[]): string[] {
    if (heads) {
        return heads
    }
    return pickNames(dtoList, select)
}

export function pickTypes(dtoList: any[], select: string[], types: DataType[]) {
    if (types) {
        return types
    }
    const length = pickNames(dtoList, select).length
    const array: DataType[] = []
    for (let i = 0; i < length; i++) {
        array.push("normal")
    }
    return array
}

export function pickBodyData(dtoList: any[], select: string[]) {
    if (select) {
        return _.map(dtoList, (dto) => _.pick(dto, select))
    }
    return dtoList
}