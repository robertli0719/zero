/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-06-14
 */
import * as React from "react"
import * as coder from "../../../../utilities/coder"

type Props = {
    val: any
}

export class DateCol extends React.Component<Props, {}>{

    render() {
        const val = this.props.val
        const num = new Number(val) as number
        if (num == NaN) {
            return <td style={{ color: "red" }}>NaN</td>
        }
        const time = coder.numberToDateString(val)
        return <td>{time}</td>
    }
}