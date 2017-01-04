/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0 2017-01-03
 */
import * as React from "react";
import * as ReactDOM from "react-dom";
import * as bs from "react-bootstrap";
import * as rb from "react-bootstrap"
import { LinkContainer } from 'react-router-bootstrap';
import { makeRandomString } from "../../utilities/random-coder"
import { http, RestErrorDto, RestErrorItemDto } from "../../utilities/http"

export type ShowTableProps = {
    dtoList: any[]
}

export type ShowTableState = {

}

export class Table extends React.Component<ShowTableProps, ShowTableState>{

    constructor(props: ShowTableProps) {
        super(props);

    }

    getHeads(): string[] {
        let array: Array<any> = this.props.dtoList;
        if (array.length == 0) {
            return [];
        }
        return Object.keys(array[0])
    }

    makeAdditionalHeads() {
        return React.Children.map(this.props.children, (child: any) => {
            return <th></th>
        })
    }

    makeAdditionalCol(rowIndex: number) {
        return React.Children.map(this.props.children, (child: any) => {
            let ele = React.cloneElement(child, {
                index: rowIndex,
                dto: this.props.dtoList[rowIndex]
            })
            return <td>{ele}</td>
        })
    }

    makeAlert(bsStyle: string, strongStr: string, text: string) {
        return (
            <bs.Alert bsStyle={bsStyle}>
                <strong>strongStr</strong> {text}
            </bs.Alert>
        );
    }

    makeHeader(heads: string[]) {
        return (
            heads.map((text) => {
                return <th>{text}</th>
            })
        );
    }

    makeBodyRow(heads: string[], dto: any) {
        return heads.map((name) => { return <td>{dto[name]}</td> });
    }

    makeBody(heads: string[]) {
        return this.props.dtoList.map((dto, rowIndex) => {
            return (
                <tr>
                    {this.makeBodyRow(heads, dto)}
                    {this.makeAdditionalCol(rowIndex)}
                </tr>
            )
        });
    }

    render() {
        if (!this.props.dtoList) {
            return this.makeAlert("warning", "No data!", "There is no data in this table.");
        } else if (this.props.dtoList instanceof Array == false) {
            return this.makeAlert("alert", "Error!", "Fail to show the table");
        } else if (this.props.dtoList.length == 0) {
            return this.makeAlert("warning", "No Rows!", "There is nothing in the table.");
        }
        let heads = this.getHeads();
        return (
            <bs.Table responsive>
                <thead>
                    <tr>
                        {this.makeHeader(heads)}
                        {this.makeAdditionalHeads()}
                    </tr>
                </thead>
                <tbody>
                    {this.makeBody(heads)}
                </tbody>
            </bs.Table>
        )
    }
}

export type ColButtonProps = {
    name: string
    onAction: (dto: any, index: number) => {}
    active?: boolean;
    block?: boolean;
    bsStyle?: string;
    bsSize?: bs.Sizes;
    index?: number
    dto?: any
}

export class ColButton extends React.Component<ColButtonProps, {}>{

    onAction() {
        this.props.onAction(this.props.dto, this.props.index);
    }

    render() {
        return (
            <bs.Button
                active={this.props.active}
                block={this.props.block}
                bsStyle={this.props.bsStyle}
                bsSize={this.props.bsSize}
                onClick={this.onAction.bind(this)}
                >
                {this.props.name}
            </bs.Button>
        )
    }
}