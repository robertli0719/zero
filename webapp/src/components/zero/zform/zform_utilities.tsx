/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.0 2017-02-19
 */
import { http, RestErrorDto } from "../../../utilities/http"
import { Selection } from "./zform_schema"

export function dtoListToOptions(dtoList: Array<any>, keyName = "name", labelName = keyName): Selection[] {
    const options: Selection[] = []
    for (const id in dtoList) {
        const dto = dtoList[id]
        const value = dto[keyName]
        const label = dto[labelName]
        options.push({ label: label, value: value })
    }
    return options
}

export function fetchSelectOptions(url: string, keyName = "name", labelName = keyName) {
    return http.get(url)
        .then((dtoList: any) => {
            return dtoListToOptions(dtoList, keyName, labelName)
        }).catch((error: RestErrorDto) => {
            console.log("Error:", error)
            throw error
        })
}