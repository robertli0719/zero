import { Action, UPDATE_ME } from "../Store"

export interface UserProfile {
    uid: string
    authLabel: string
    userTypeName: string
    userPlatformName: string
    name: string
    telephone: string
    roleList: string[]
}

let initState: UserProfile = {
    uid: null,
    authLabel: null,
    userTypeName: null,
    userPlatformName: null,
    name: null,
    telephone: null,
    roleList: null
}

export function meReducer(state: UserProfile = initState, action: Action): any {
    switch (action.type) {
        case UPDATE_ME:
            state = $.extend({}, state, action.payload);
        default:
            return state;
    }
}