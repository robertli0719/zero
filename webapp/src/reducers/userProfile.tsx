import { Action, PUT_USER_PROFILE } from "../Store"

let valState: number;

export interface UserProfileState {
    authLabel: string
    userType: string
    name: string
    telephone: string
}

let initState: UserProfileState = {
    authLabel: null,
    userType: null,
    name: null,
    telephone: null
}

export function userProfileReducer(state: UserProfileState = initState, action: Action): any {
    switch (action.type) {
        case PUT_USER_PROFILE:
            state = $.extend({}, state, { userProfile: action.payload });
        default:
            return state;
    }
}