import { createStore, Action } from "redux"

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
}

let initState: AppState = { val: 0 }

function reducer(state: AppState = initState, action: Action): any {
    switch (action.type) {
        case "ADD_NUMBER":
            state = $.extend({}, state);
            state.val = state.val + 1;
            return state;
        default:
            return state;
    }

}

export let store = createStore(reducer);