/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.2 2017-03-29
 */
import * as React from "react"
import * as rb from "react-bootstrap"
import { makeRandomString } from "../../../utilities/random-coder"
import { http, RestErrorDto, RestErrorItemDto, Pagination, HttpContent } from "../../../utilities/http"
import * as _ from "lodash"
import * as dataPicker from "./zview_data_picker"
import { ViewAlert } from "./zview_alert"
import { ViewHead } from "./zview_head"
import { ViewBody } from "./zview_body"
import { ViewCounter } from "./zview_counter"
import { ViewLinkGroup } from "./zview_Link_group"
import { UpdateEventListener } from "../event/UpdateEventListener"

export * from "./zview_children/col_button"

export type ViewProps = {
    header?: string
    bsStyle?: string
    uri: string
    heads?: string[]
    select?: string[]
    updateEventListener?: UpdateEventListener
}

export type ViewState = {
    additionalColElements: JSX.Element[]
    uri: string
    dtoList: any[]
    pagination: Pagination
    links: { [key: string]: string }
}

export class View extends React.Component<ViewProps, ViewState>{

    constructor(props: ViewProps) {
        super(props)

        const additionalColElements = [] as JSX.Element[]
        React.Children.map(this.props.children, (child: any) => {
            const ele: JSX.Element = React.cloneElement(child)
            additionalColElements.push(ele)
        })

        this.state = {
            additionalColElements: additionalColElements,
            uri: props.uri,
            dtoList: [],
            pagination: undefined,
            links: {}
        }

        const eventListener = this.props.updateEventListener
        if (eventListener) {
            eventListener.addAction(this.componentDidMount.bind(this))
        }
    }

    componentDidMount() {
        this.fetchContent()
    }

    componentWillReceiveProps(nextProps: ViewProps, nextContext: any): void {
        if (this.props.uri != nextProps.uri) {
            this.setState({ uri: nextProps.uri })
            setTimeout(this.fetchContent.bind(this), 0)
        }
    }

    fetchContent() {
        http.getContent(this.state.uri)
            .then((content: HttpContent) => {
                this.setState({
                    dtoList: content.body,
                    pagination: content.pagination,
                    links: content.links
                })
            })
    }

    updateUri(uri: string) {
        this.setState({ uri: uri })
        setTimeout(this.fetchContent.bind(this), 0)
    }

    makeTotalView() {
        const pagination = this.state.pagination
        if (pagination) {
            return (
                <div>
                    <h3>Total items found: {pagination.count}</h3>
                    <p></p>
                </div>
            )
        }
        return null
    }

    render() {
        if (!this.state.dtoList) {
            return <ViewAlert bsStyle="warning" title="No data!" text="There is no data in this table." />
        } else if (this.state.dtoList instanceof Array == false) {
            return <ViewAlert bsStyle="alert" title="Error!" text="Fail to show the table." />
        } else if (this.state.dtoList.length == 0) {
            return <ViewAlert bsStyle="warning" title="No Rows!" text="There is nothing in the table." />
        }
        const dtoList = this.state.dtoList
        const names = dataPicker.pickNames(dtoList, this.props.select)
        const heads = dataPicker.pickHeads(dtoList, this.props.select, this.props.heads)
        const bodyData = dataPicker.pickBodyData(dtoList, this.props.select)
        const additionalColNumber = this.state.additionalColElements.length
        const additionalColElements = this.state.additionalColElements
        return (
            <rb.Panel header={this.props.header} bsStyle={this.props.bsStyle}>
                {this.makeTotalView()}
                <rb.Table responsive>
                    <ViewHead heads={heads} additionalColNumber={additionalColNumber} />
                    <ViewBody names={names} dtoList={dtoList} data={bodyData} additionalColElements={additionalColElements} />
                </rb.Table >
                <ViewCounter pagination={this.state.pagination} />
                <ViewLinkGroup links={this.state.links} updateUri={this.updateUri.bind(this)} />
            </rb.Panel>
        )
    }
}