/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */

/*    
    author: robert li
    version: 2016-12-21 1.0.0
*/

const CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

export function makeRandomString(length: number) {
    let text = "";
    for (var i = 0; i < length; i++) {
        text += CHARS.charAt(Math.floor(Math.random() * CHARS.length));
    }
    return text;
}

