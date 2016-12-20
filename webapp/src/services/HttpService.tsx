/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */

/*
    I create this code for using REST API based on jquery.
    
    author: robert li
    version: 2016-12-19 1.0.2
*/

export type RestErrorDto = {
    type: string
    source: string
    message: string
    detail: string
}

class HttpService {

    private prefix: string = "api/v1/";

    public get(url: string, success: (data: any) => any, error: (jqXHR: JQueryXHR) => any) {
        $.ajax({
            url: this.prefix + url,
            method: "get",
            success: success,
            error: error
        });
    }

    public post(url: string, dto: any, success: (data: any) => any, error: (jqXHR: JQueryXHR) => any) {
        let json = JSON.stringify(dto);
        $.ajax({
            url: this.prefix + url,
            method: "post",
            contentType: "application/json;charset=UTF-8",
            data: json,
            success: success,
            error: error
        });
    }

    public put(url: string, dto: any, success: (data: any) => any, error: (jqXHR: JQueryXHR) => any) {
        let json = JSON.stringify(dto);
        $.ajax({
            url: this.prefix + url,
            method: "put",
            contentType: "application/json;charset=UTF-8",
            data: json,
            success: success,
            error: error
        });
    }

    public delete(url: string, success: (data: any) => any, error: (jqXHR: JQueryXHR) => any) {
        $.ajax({
            url: this.prefix + url,
            method: "delete",
            success: success,
            error: error
        });
    }
}

export let httpService = new HttpService();