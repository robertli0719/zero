/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-06-14
 */
import * as React from "react"

type Props = {
    val: any
}

export class CurrencyCol extends React.Component<Props, {}>{

    render() {
        const inputVal = this.props.val
        const num = new Number(inputVal)
        if (num == NaN) {
            return <td style={{ color: "red" }}>NaN</td>
        }
        const val = num >= 0 ? num : -num
        const valStr = val.toFixed(2)
        if (num < 0) {
            return <td className="text-right" style={{ color: "red" }}>(${valStr})</td>
        } else {
            return <td className="text-right">${valStr}</td>
        }
    }
}