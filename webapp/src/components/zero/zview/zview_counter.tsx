/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-03-28
 */
import * as React from "react"
import * as rb from "react-bootstrap"
import { Pagination } from "../../../utilities/http"

type ViewCounterProps = {
    pagination: Pagination
}

type State = {

}

export class ViewCounter extends React.Component<ViewCounterProps, State>{

    countTotalPage(count: number, limit: number): number {
        const fullPageNumber = parseInt((count / limit) + "")
        const additionPageNumber = count % limit ? 1 : 0
        return fullPageNumber + additionPageNumber
    }

    render() {
        const pagination = this.props.pagination
        if (!pagination) {
            return null
        }
        const count = pagination.count
        const offset = pagination.offset
        const limit = pagination.limit
        const page = 1 + parseInt("" + (offset / limit))
        const totalPage = this.countTotalPage(count, limit)
        return (
            <div>
                <p className="text-center">Page: {page} / {totalPage}</p>
                <p className="text-center">({limit} items per page)</p>
            </div>
        )
    }
}

