/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.2 2017-05-02
 */
import * as React from "react"

export type Props = {
    dtoList: any[]
    data: any[]
    names: string[]
    additionalColElements: JSX.Element[]
}

export class ViewBody extends React.Component<Props, {}>{

    makeDataRow(dto: any) {
        return this.props.names.map((name) => {
            if (name == "imgUrl" || name == "logoUrl") {
                return (
                    <td>
                        <img className="img-responsive"
                            src={dto[name]}
                            style={{ maxHeight: 200, maxWidth: 200 }}
                        />
                    </td>
                )
            } else if (dto[name] instanceof String) {
                return <td>{dto[name]}</td>
            } else if (dto[name] === true) {
                return <td className="text-center">Yes</td>
            } else if (dto[name] === false) {
                return <td className="text-center">-</td>
            } else if(dto[name] instanceof Object){
                return <td className="text-center">[Object]</td>
            }
            return <td>{dto[name]}</td>
        })
    }

    makeAdditionalCol(rowIndex: number) {
        const additionColElements = this.props.additionalColElements
        return React.Children.map(additionColElements, (child: any) => {
            const ele = React.cloneElement(child, {
                index: rowIndex,
                dto: this.props.dtoList[rowIndex]
            })
            return <td>{ele}</td>
        })
    }

    makeBody() {
        return this.props.data.map((obj, rowIndex) => {
            return (
                <tr>
                    {this.makeDataRow(obj)}
                    {this.makeAdditionalCol(rowIndex)}
                </tr>
            )
        })
    }

    render() {
        return (
            <tbody>
                {this.makeBody()}
            </tbody>
        )
    }
}