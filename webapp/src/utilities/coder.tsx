/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */

/*    
    author: robert li
    version: 2016-06-14 1.0.0
*/

const CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"

export function makeRandomString(length: number) {
    let text = ""
    for (var i = 0; i < length; i++) {
        text += CHARS.charAt(Math.floor(Math.random() * CHARS.length))
    }
    return text
}

export function changeToAccountStyle(str: string): string {
    let result = "", count = 0
    for (let c of str) {
        if (count > 1 && count % 4 == 0) {
            result += "-"
        }
        result += c
        count++
    }
    return result
}

export function numberToString(num: number, length: number) {
    let str = num + ""
    while (str.length < length) {
        str = "0" + str
    }
    return str
}

export function numberToDateString(time: number): string {
    const t = new Date(time)
    const year = t.getFullYear()
    const monthVal = t.getMonth() + 1
    const dateVal = t.getDate()
    const month = numberToString(monthVal, 2)
    const date = numberToString(dateVal, 2)
    return year + "-" + month + "-" + date
}

export function numberToTimeString(time: number): string {
    const t = new Date(time)
    const hourVal = t.getHours()
    const minuteVal = t.getMinutes()
    const secondVal = t.getSeconds()
    const hour = numberToString(hourVal, 2)
    const minute = numberToString(minuteVal, 2)
    const second = numberToString(secondVal, 2)
    return hour + ":" + minute + ":" + second
}

export function numberToDatetimeString(time: number): string {
    const d = numberToDateString(time)
    const t = numberToTimeString(time)
    return d + " " + t
}