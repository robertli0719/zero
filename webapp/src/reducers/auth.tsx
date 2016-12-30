import { Action, UPDATE_AUTH } from "../Store"

let valState: number;

export interface Auth {
    authLabel: string
    userTypeName: string
    name: string
    telephone: string
}

let initState: Auth = {
    authLabel: null,
    userTypeName: null,
    name: null,
    telephone: null
}

export function authReducer(state: Auth = initState, action: Action): any {
    switch (action.type) {
        case UPDATE_AUTH:
            state = $.extend({}, state, action.payload);
        default:
            return state;
    }
}