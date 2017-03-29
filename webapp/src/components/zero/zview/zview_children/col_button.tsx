/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-03-28
 */
import * as React from "react"
import * as bs from "react-bootstrap"

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