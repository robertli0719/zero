/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
import { Promise } from "es6-promise"
import * as fetch from 'isomorphic-fetch'

/*
    I create this code for using REST API for Zero.
    
    author: robert li
    version: 2016-12-26 1.0
*/

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

function is2xx(res: IResponse): boolean {
    let status = res.status;
    return status >= 200 && status < 300
}

function isJsonBody(res: IResponse): boolean {
    var contentType = res.headers.get("content-type");
    return contentType && contentType.indexOf("application/json") !== -1
}

function createErrorDto(status: string) {
    let restError: RestErrorDto = {
        status: status,
        errors: []
    }
}

class HttpService {

    private prefix: string = "api/v1/";

    public get(url: string) {
        return fetch(this.prefix + url, {
            credentials: 'include'
        }).then((res: IResponse) => {
            if (isJsonBody(res)) {
                return res.json().then((json) => {
                    if (is2xx(res)) {
                        return json;
                    }
                    throw json;
                });
            }
            console.log("Oops, we haven't got JSON!");
            throw createErrorDto("RESULT_IS_NOT_JSON");
        });
    }

    public post(url: string, dto: any) {
        let json = JSON.stringify(dto);
        return fetch(this.prefix + url, {
            method: "POST",
            credentials: 'include',
            headers: { "Content-Type": "application/json;charset=UTF-8" },
            body: json
        }).then((res: IResponse) => {
            if (is2xx(res)) {
                return;
            } else if (isJsonBody(res)) {
                return res.json().then((json) => {
                    throw json;
                });
            }
            console.log("Oops, we haven't got JSON!");
            throw createErrorDto("RESULT_IS_NOT_JSON");
        });
    }

    public put(url: string, dto: any) {
        let json = JSON.stringify(dto);
        return fetch(this.prefix + url, {
            method: "PUT",
            credentials: 'include',
            headers: { "Content-Type": "application/json;charset=UTF-8" },
            body: json
        }).then((res: IResponse) => {
            if (is2xx(res)) {
                return;
            } else if (isJsonBody(res)) {
                return res.json().then((json) => {
                    throw json;
                });
            }
            console.log("Oops, we haven't got JSON!");
            throw createErrorDto("RESULT_IS_NOT_JSON");
        });
    }

    public delete(url: string) {
        return fetch(this.prefix + url, {
            method: "DELETE",
            credentials: 'include'
        }).then((res) => {
            if (is2xx(res)) {
                return;
            } else if (isJsonBody(res)) {
                return res.json().then((json) => {
                    throw json;
                });
            }
            console.log("Oops, we haven't got JSON!");
            throw createErrorDto("RESULT_IS_NOT_JSON");
        });
    }
}

export let http = new HttpService();