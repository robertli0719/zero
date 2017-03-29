/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-03-28
 */
import * as _ from "lodash"

export function pickNames(dtoList: any[], select: string[]) {
    if (select) {
        return select
    }
    const array: Array<any> = dtoList;
    return Object.keys(array[0])
}

export function pickHeads(dtoList: any[], select: string[], heads: string[]): string[] {
    if (heads) {
        return heads
    }
    return pickNames(dtoList, select)
}

export function pickBodyData(dtoList: any[], select: string[]) {
    if (select) {
        return _.map(dtoList, (dto) => _.pick(dto, select))
    }
    return dtoList
}