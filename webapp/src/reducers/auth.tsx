import { Action, UPDATE_AUTH } from "../Store"

let valState: number;

export interface Auth {
    authLabel: string
    userType: string
    name: string
    telephone: string
}

let initState: Auth = {
    authLabel: null,
    userType: null,
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