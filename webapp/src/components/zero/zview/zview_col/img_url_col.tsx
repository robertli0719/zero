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

export class ImgUrlCol extends React.Component<Props, {}>{

    render() {
        return (
            <td>
                <img className="img-responsive"
                    src={this.props.val}
                    style={{ maxHeight: 200, maxWidth: 200 }}
                />
            </td>
        )
    }
}