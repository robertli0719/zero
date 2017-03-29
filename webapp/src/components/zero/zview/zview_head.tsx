/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-03-28
 */
import * as React from "react"

export type Props = {
    heads: string[]
    additionalColNumber: number
}

export class ViewHead extends React.Component<Props, {}>{

    makeHeader(heads: string[]) {
        return (
            heads.map((text) => {
                return <th>{text}</th>
            })
        )
    }

    makeAdditionalHeads() {
        const itemArray: JSX.Element[] = []
        for (let i = 0; i < this.props.additionalColNumber; i++) {
            itemArray.push(<th></th>)
        }
        return itemArray
    }

    render() {
        return (
            <thead>
                <tr>
                    {this.makeHeader(this.props.heads)}
                    {this.makeAdditionalHeads()}
                </tr>
            </thead>
        )
    }
}