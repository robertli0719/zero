/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.0 2017-03-01
 */
import * as React from "react"

type IFProps = {
    test: boolean
}

export class IF extends React.Component<IFProps, {}>{

    render() {
        if (!this.props.test)
            return null
        return <div>{this.props.children}</div>
    }
}