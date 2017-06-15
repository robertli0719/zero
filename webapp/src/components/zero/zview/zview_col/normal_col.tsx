/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-06-14
 */
import * as React from "react"
import { ImgUrlCol } from "./img_url_col"

type Props = {
    dto: any
    name: string
}

export class NormalCol extends React.Component<Props, {}>{

    render() {
        const dto = this.props.dto
        const name = this.props.name
        if (name == "imgUrl" || name == "logoUrl") {
            return <ImgUrlCol val={dto[name]} />
        } else if (dto[name] instanceof String) {
            return <td>{dto[name]}</td>
        } else if (dto[name] === true) {
            return <td className="text-center">Yes</td>
        } else if (dto[name] === false) {
            return <td className="text-center">-</td>
        } else if (dto[name] instanceof Object) {
            return <td className="text-center">[Object]</td>
        }
        return <td>{dto[name]}</td>
    }
}