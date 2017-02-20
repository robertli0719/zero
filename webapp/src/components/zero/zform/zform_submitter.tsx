/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.0 2017-02-19
 */
import { http } from "../../../utilities/http"
export type Request = {
    uri: string
    method: string
    data: any
}

function getSumbitMethod(request: Request) {
    let method = "POST";
    if (request.method) {
        method = request.method
    }
    return method.toUpperCase()
}

export function submit(request: Request): Promise<never> {
    const uri = request.uri
    const method = getSumbitMethod(request)
    const dto = request.data
    if (!uri) {
        return
    }
    switch (method) {
        case "GET":
            return http.get(uri);
        case "POST":
            return http.post(uri, dto);
        case "PUT":
            return http.put(uri, dto);
        case "DELETE":
            return http.delete(uri);
    }
    throw "Error: Can't support method: " + method
}
