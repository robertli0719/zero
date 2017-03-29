/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-03-28
 */
import * as React from "react"
import { ButtonGroup, Button } from "react-bootstrap"

type Props = {
    links: { [key: string]: string }
    updateUri: (uri: string) => void
}

interface btnInfo {
    className: string
    url: string
}

export class ViewLinkGroup extends React.Component<Props, {}>{

    makeButtonList(): btnInfo[] {
        const first = this.props.links["first"]
        const prev = this.props.links["prev"]
        const next = this.props.links["next"]
        const last = this.props.links["last"]

        const btnList: btnInfo[] = []
        btnList.push({ className: "glyphicon glyphicon glyphicon-step-backward", url: this.props.links["first"] })
        btnList.push({ className: "glyphicon glyphicon-chevron-left", url: this.props.links["prev"] })
        btnList.push({ className: "glyphicon glyphicon-chevron-right", url: this.props.links["next"] })
        btnList.push({ className: "glyphicon glyphicon glyphicon-step-forward", url: this.props.links["last"] })
        return btnList
    }

    render() {
        const btnInfos = this.makeButtonList()
        return (
            <div className="text-center">
                <ButtonGroup>
                    {btnInfos.map((val) => {
                        if (val.url) {
                            return <Button onClick={this.props.updateUri.bind(this, val.url)}><span className={val.className}></span></Button>
                        }
                        else {
                            return <Button disabled={true}><span className={val.className}></span></Button>
                        }
                    })}
                </ButtonGroup>
            </div>
        )
    }
}