/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * dependencies: react-cropper 0.10.0
 * version 1.0.3 2017-02-21
 */
import * as React from "react"
import * as ReactDOM from "react-dom"
import ReactCropper from "react-cropper"

export type Props = {
    src: string
    style?: React.CSSProperties
    aspectRatio?: number
    onChange: (result: CropResult) => never
    resetFunBack?: (reset: () => void) => {}
}

type State = {
    cropper: any
}

export type CropResult = {
    x: number
    y: number
    width: number
    height: number
}

class MyCropper extends ReactCropper {

    constructor(props: Props) {
        super()
        let anyProps: any = props;
        if (anyProps.resetFunThief) {
            anyProps.resetFunThief(super.reset.bind(this));
        }
    }
}

export class Cropper extends React.Component<Props, State> {

    constructor(props: Props) {
        super(props);
        let cropper: any = <MyCropper crop={this.onChange.bind(this)} />
        if (this.props.resetFunBack) {
            cropper.props = $.extend(cropper.props, this.props, { resetFunThief: this.props.resetFunBack });
        } else {
            cropper.props = $.extend(cropper.props, this.props);
        }
        this.state = { cropper: cropper }
    }

    onChange(event: any) {
        const data = event.detail
        this.props.onChange(data);
    }

    render() {
        return <div>{this.state.cropper}</div>
    }
}