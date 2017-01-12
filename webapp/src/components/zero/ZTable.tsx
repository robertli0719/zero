/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.3 2017-01-09
 */
import * as React from "react";
import * as ReactDOM from "react-dom";
import * as bs from "react-bootstrap";
import * as rb from "react-bootstrap"
import { LinkContainer } from 'react-router-bootstrap';
import { makeRandomString } from "../../utilities/random-coder"
import { http, RestErrorDto, RestErrorItemDto } from "../../utilities/http"
import * as _ from "lodash"

export type ShowTableProps = {
    dtoList: any[]
    heads?: string[]
    select?: string[]
}

export type ShowTableState = {

}

export class Table extends React.Component<ShowTableProps, ShowTableState>{

    constructor(props: ShowTableProps) {
        super(props);
        let a = this.getBodys();
    }

    getNames(): string[] {
        if (this.props.select) {
            return this.props.select
        }
        let array: Array<any> = this.props.dtoList;
        return Object.keys(array[0])
    }

    getHeads(): string[] {
        let array: Array<any> = this.props.dtoList;
        if (array.length == 0) {
            return [];
        } else if (this.props.heads) {
            return this.props.heads;
        }
        if (this.props.select) {
            return this.props.select;
        }
        return Object.keys(array[0])
    }

    getBodys(): any[] {
        if (this.props.select) {
            return _.map(this.props.dtoList, (dto) => _.pick(dto, this.props.select))
        }
        return this.props.dtoList;
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
                <strong>Info</strong> {text}
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

    makeBodyRow(dto: any) {
        return this.getNames().map((name) => {
            if (name == "imgUrl") {
                return <td><img className="img-responsive" src={dto[name]} style={{}} /></td>
            } else if (dto[name] instanceof String) {
                return <td>{dto[name]}</td>
            } else if (dto[name] === true) {
                return <td className="text-center">Yes</td>
            } else if (dto[name] === false) {
                return <td className="text-center">-</td>
            }
            return <td>{dto[name]}</td>
        });
    }

    makeBody() {
        return this.getBodys().map((obj, rowIndex) => {
            return (
                <tr>
                    {this.makeBodyRow(obj)}
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
                    {this.makeBody()}
                </tbody>
            </bs.Table>
        )
    }
}

export type ColButtonProps = {
    name: string
    onAction: (dto: any, index: number) => {}
    onRender?: (dto: any, index: number) => boolean
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
        if (this.props.onRender) {
            const display: boolean = this.props.onRender(this.props.dto, this.props.index)
            if (display == false) {
                return <span></span>
            }
        }
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