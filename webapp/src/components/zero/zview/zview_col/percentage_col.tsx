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

export class PercentageCol extends React.Component<Props, {}>{

    render() {
        const inputVal = this.props.val
        const num = new Number(inputVal * 100)
        const val = num.toFixed(0)

        return (
            <td className="text-right">
                {val}%
            </td>
        )
    }
}