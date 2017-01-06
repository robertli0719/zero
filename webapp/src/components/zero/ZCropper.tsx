/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * dependencies: react-cropper 0.10.0
 * version 1.0 2017-01-04
 */
import * as React from "react"
import * as ReactDOM from "react-dom"
import ReactCropper from "react-cropper"
import "../../../node_modules/react-cropper/node_modules/cropperjs/dist/cropper.css"

export type Props = {
    src: string
    style?: React.CSSProperties
    aspectRatio?: number
    onChange: (result: CropResult) => never
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

export class Cropper extends React.Component<Props, State> {

    constructor(props: Props) {
        super(props);
        let cropper = <ReactCropper crop={this.onChange.bind(this)} />
        cropper.props = $.extend(cropper.props, this.props);
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