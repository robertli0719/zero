/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.3 2017-06-14
 */
import * as React from "react"
import { DataType } from "./zview_schema"
import { ImgUrlCol } from "./zview_col/img_url_col"
import { NormalCol } from "./zview_col/normal_col"
import { CurrencyCol } from "./zview_col/currency_col"
import { AccountingCol } from "./zview_col/accounting_col"
import { DateCol } from "./zview_col/date_col"
import { DatetimeCol } from "./zview_col/datetime_col"
import { TimeCol } from "./zview_col/time_col"
import { PercentageCol } from "./zview_col/percentage_col"
import { AccountCol } from "./zview_col/account_col"

export type Props = {
    dtoList: any[]
    data: any[]
    names: string[]
    types: DataType[]
    additionalColElements: JSX.Element[]
}

export class ViewBody extends React.Component<Props, {}>{

    makeDataRow(dto: any) {
        const types = this.props.types
        return this.props.names.map((name, rowIndex: number) => {
            const val = dto[name]
            switch (types[rowIndex]) {
                case "normal":
                    return <NormalCol dto={dto} name={name} />
                case "string":
                    return <td>{"" + val}</td>
                case "integer":
                    return <td>{parseInt(val)}</td>
                case "float":
                    return <td>{parseFloat(val)}</td>
                case "currency":
                    return <CurrencyCol val={val} />
                case "accounting":
                    return <AccountingCol val={val} />
                case "date":
                    return <DateCol val={val} />
                case "time":
                    return <TimeCol val={val} />
                case "datetime":
                    return <DatetimeCol val={val} />
                case "percentage":
                    return <PercentageCol val={val} />
                case "account":
                    return <AccountCol val={val} />
                case "img-url":
                    return <ImgUrlCol val={dto[name]} />

            }
            return <td style={{ color: "red" }}>#NAME?</td>
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