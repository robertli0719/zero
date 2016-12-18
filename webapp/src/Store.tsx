import { createStore } from "redux"
import * as a from "./ActionCreater"

export type UserAuthDto = {
    userType: string
    platform: string
    username: string
    password: string
}

export type UserProfileDto = {
    authLabel: string
    userType: string
    name: string
    telephone: string
}

export interface AppState {
    val: number
    userProfile: UserProfileDto
}

let initState: AppState = { val: 0, userProfile: null }

function reducer(state: AppState = initState, action: a.Action): any {
    switch (action.type) {
        case a.ADD_NUMBER:
            state = $.extend({}, state);
            state.val = state.val + 1;
            return state;
        case a.PUT_USER_PROFILE:
            state = $.extend({}, state, { userProfile: action.payload });
        default:
            return state;
    }

}

export let store = createStore(reducer);