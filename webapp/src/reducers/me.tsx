import { Action, UPDATE_ME } from "../Store"

export interface UserProfile {
    authLabel: string
    userTypeName: string
    userPlatformName: string
    name: string
    telephone: string
}

let initState: UserProfile = {
    authLabel: null,
    userTypeName: null,
    userPlatformName: null,
    name: null,
    telephone: null
}

export function meReducer(state: UserProfile = initState, action: Action): any {
    switch (action.type) {
        case UPDATE_ME:
            state = $.extend({}, state, action.payload);
        default:
            return state;
    }
}