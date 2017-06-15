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

export class AccountingCol extends React.Component<Props, {}>{

    render() {
        const inputVal = this.props.val
        const num = new Number(inputVal)
        if (num == NaN) {
            return <td>{inputVal}</td>
        }
        const fixedVal = num.toFixed(2)
        const val = fixedVal == "0.00" ? "-" : fixedVal
        return (
            <td>
                <span className="pull-left">$</span>
                <span className="pull-right">{val}</span>
            </td>
        )
    }
}