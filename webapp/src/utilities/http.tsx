/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
import { Promise } from "es6-promise"
import * as fetch from 'isomorphic-fetch'

/*
    I create this code for using REST API for Zero.
    
    author: robert li
    version: 2018-08-17 1.0.5
*/

type map = { [key: string]: string }

export type RestErrorItemDto = {
    type: string
    source: string
    message: string
    detail: string
}

export type RestErrorDto = {
    status: string
    errors: RestErrorItemDto[]
}

export type HttpContent = {
    body: any
    links: map
    pagination: Pagination
}

export type Pagination = {
    count: number
    limit: number
    offset: number
}

function perprocessLinksForCommon(link: any) {
    let str = ""
    let inBracket = false
    for (let c of link) {
        if (c == '<') {
            inBracket = true
        } else if (c == ">") {
            inBracket = false
        }
        if (inBracket && c == ',') {
            str += "%comma%"
        } else {
            str += c
        }
    }
    return str
}

function changeCommonBack(array: any) {
    for (const i in array) {
        array[i] = array[i].replace(new RegExp("%comma%", 'g'), ",")
    }
    return array
}

function getLinks(res: ResponseInterface): map {
    const link = res.headers.get("Link")
    if (link == undefined) {
        return {}
    }
    const links = {} as map
    const linkWithPerprocess = perprocessLinksForCommon(link)
    const array = changeCommonBack(linkWithPerprocess.split(","))

    console.log("array", array)
    for (const str of array) {
        const url = str.match(/<(.+)>/)[1]
        const rel = str.match(/rel="(.+)"/)[1]
        links[rel] = url
    }
    return links
}

function getPagination(res: ResponseInterface): Pagination {
    const count = res.headers.get("X-Pagination-Count")
    const limit = res.headers.get("X-Pagination-Limit")
    const offset = res.headers.get("X-Pagination-Offset")
    if (count == undefined) {
        return undefined
    }
    return { count: +count, limit: +limit, offset: +offset }
}


function is2xx(res: ResponseInterface): boolean {
    const status = res.status
    return status >= 200 && status < 300
}

function isJsonBody(res: ResponseInterface): boolean {
    const contentType = res.headers.get("content-type")
    return contentType && contentType.indexOf("application/json") !== -1
}

function createErrorDto(status: string) {
    const restError: RestErrorDto = {
        status: status,
        errors: []
    }
}

class HttpService {

    private prefix: string = "api/v1/"

    private processUrl(url: string) {
        if (url.substring(0, 4) == "http") {
            return url
        }
        return this.prefix + url
    }

    public get(url: string) {
        const realUrl = this.processUrl(url)
        return fetch(realUrl, {
            credentials: 'include'
        }).then((res: ResponseInterface) => {
            if (isJsonBody(res)) {
                return res.json().then((json) => {
                    if (is2xx(res)) {
                        return json
                    }
                    throw json
                })
            }
            console.log("Oops, we haven't got JSON!")
            throw createErrorDto("RESULT_IS_NOT_JSON")
        })
    }

    public getContent(url: string) {
        const realUrl = this.processUrl(url)
        return fetch(realUrl, {
            credentials: 'include'
        }).then((res: ResponseInterface) => {
            if (isJsonBody(res)) {
                return res.json().then((json) => {
                    if (is2xx(res)) {
                        const links = getLinks(res)
                        const pagination = getPagination(res)
                        return { body: json, links: links, pagination: pagination } as HttpContent
                    }
                    throw json
                })
            }
            console.log("Oops, we haven't got JSON!")
            throw createErrorDto("RESULT_IS_NOT_JSON")
        })
    }

    public post(url: string, dto: any) {
        const json = JSON.stringify(dto)
        const realUrl = this.processUrl(url)
        return fetch(realUrl, {
            method: "POST",
            credentials: 'include',
            headers: { "Content-Type": "application/json;charset=UTF-8" },
            body: json
        }).then((res: ResponseInterface) => {
            if (is2xx(res)) {
                return
            } else if (isJsonBody(res)) {
                return res.json().then((json) => {
                    throw json
                })
            }
            console.log("Oops, we haven't got JSON!")
            throw createErrorDto("RESULT_IS_NOT_JSON")
        })
    }

    public postParams(url: string, params: any) {
        const realUrl = this.processUrl(url)
        return fetch(realUrl, {
            method: "POST",
            credentials: 'include',
            body: params
        }).then((res: ResponseInterface) => {
            if (is2xx(res)) {
                return res.text()
            } else if (isJsonBody(res)) {
                return res.json().then((json) => {
                    throw json
                })
            }
            console.log("Oops, we haven't got JSON!")
            throw createErrorDto("RESULT_IS_NOT_JSON")
        })
    }

    public put(url: string, dto: any) {
        const realUrl = this.processUrl(url)
        const json = JSON.stringify(dto)
        return fetch(realUrl, {
            method: "PUT",
            credentials: 'include',
            headers: { "Content-Type": "application/json;charset=UTF-8" },
            body: json
        }).then((res: ResponseInterface) => {
            if (is2xx(res)) {
                return
            } else if (isJsonBody(res)) {
                return res.json().then((json) => {
                    throw json
                })
            }
            console.log("Oops, we haven't got JSON!")
            throw createErrorDto("RESULT_IS_NOT_JSON")
        })
    }

    public delete(url: string) {
        const realUrl = this.processUrl(url)
        return fetch(realUrl, {
            method: "DELETE",
            credentials: 'include'
        }).then((res) => {
            if (is2xx(res)) {
                return
            } else if (isJsonBody(res)) {
                return res.json().then((json) => {
                    throw json
                })
            }
            console.log("Oops, we haven't got JSON!")
            throw createErrorDto("RESULT_IS_NOT_JSON")
        })
    }
}

export const http = new HttpService()