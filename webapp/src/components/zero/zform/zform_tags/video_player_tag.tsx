/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-04-11
 */
import * as React from "react"
import { Button } from "react-bootstrap"
import { ZFormTag, ZFormTagAttr, registerTagRender, nopicUrl } from "../zform_tag"

registerTagRender("videoplayer", (tagAttr: ZFormTagAttr) => {
    return <VideoPlayerTag attr={tagAttr} />
})

class VideoPlayerTag extends ZFormTag {

    render() {
        const attr = this.props.attr
        const value = attr.value
        const src = attr.value ? attr.value.toString() : null
        if (attr.value) {
            return <video width="100%" controls>
                <source src={src} type="video/mp4" />
                Your device does not support mp4 video.
            </video>
        }
        return null
    }
}