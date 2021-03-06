/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.1 2017-04-11
 */
import * as React from "react"
import { Button } from "react-bootstrap"
import { ZFormTag, ZFormTagAttr, registerTagRender, nopicUrl } from "../zform_tag"

registerTagRender("imageshower", (tagAttr: ZFormTagAttr) => {
    return <ImageShowerTag attr={tagAttr} />
})


class ImageShowerTag extends ZFormTag {

    render() {
        const attr = this.props.attr
        const value = attr.value
        if (value instanceof Array) {
            const urlArray = value as string[]
            return <div>
                {
                    urlArray.map((imgUrl) => {
                        return <img src={imgUrl} style={{ maxWidth: "100%" }} />
                    })
                }
            </div>
        }
        const src = attr.value ? attr.value.toString() : nopicUrl
        return <img src={src} style={{ maxHeight: 200, maxWidth: "100%" }} />
    }
}