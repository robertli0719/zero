import { Action } from "../Store"

export enum RequestState {
    PROCESSING, SUCCESS, FAIL
}

export type RestError = {
    type: string
    source: string
    message: string
    detail: string
}

export type RequestResult = {
    state: RequestState
    restError: RestError
}

export interface RequestStoreState {
    [key: string]: RequestResult
}

let initState: RequestStoreState = {}

export function requestStoreReducer(state: RequestStoreState = initState, action: Action): any {
    switch (action.type) {
        default:
            return state;
    }
}