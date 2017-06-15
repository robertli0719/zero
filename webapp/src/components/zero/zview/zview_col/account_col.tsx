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

export class AccountCol extends React.Component<Props, {}>{

    render() {
        const str = this.props.val + ""
        const result = coder.changeToAccountStyle(str)
        return <td>{result}</td>
    }
}