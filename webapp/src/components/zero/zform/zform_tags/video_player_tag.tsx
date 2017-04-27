/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.1 2017-04-12
 */
import * as React from "react"
import { Button } from "react-bootstrap"
import { makeRandomString } from "../../../../utilities/random-coder"
import { ZFormTag, ZFormTagProps, ZFormTagAttr, registerTagRender, nopicUrl } from "../zform_tag"

registerTagRender("videoplayer", (tagAttr: ZFormTagAttr) => {
    return <VideoPlayerTag attr={tagAttr} />
})

class VideoPlayerTag extends ZFormTag {

    private controlId = makeRandomString(32)
    private videoTag: JSX.Element = null

    constructor(props: ZFormTagProps) {
        super(props)
        const src = props.attr.value ? props.attr.value.toString() : null
        this.videoTag = this.makeVideoTag(src)
    }

    makeVideoTag(src: string) {
        return <video width="100%" controls>
            <source src={src} type="video/mp4" />
            <p>Your device does not support mp4 video.</p>
        </video>
    }

    render() {
        const attr = this.props.attr
        const value = attr.value
        const src = attr.value ? attr.value.toString() : null
        if (attr.value) {
            return this.videoTag
        }
        return null
    }

    componentWillReceiveProps(nextProps: ZFormTagProps, nextContext: any): void {
        if (nextProps.attr.value != this.props.attr.value) {
            const src = nextProps.attr.value ? nextProps.attr.value.toString() : null
            this.videoTag = null
            setTimeout(() => {
                this.videoTag = this.makeVideoTag(src)
                this.forceUpdate()
            }, 0)
        }
    }
}