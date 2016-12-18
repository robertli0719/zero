import * as s from "./Store"

export interface Action {
    type: string;
    payload?: any;
    meta?: string
}

export const ADD_NUMBER = "ADD_NUMBER"
export const PUT_USER_PROFILE = "PUT_USER_PROFILE"

class ActionCreater {
    addNumber(val: number): Action {
        return {
            type: ADD_NUMBER,
            payload: val,
            meta: "increasse the number in state"
        }
    }

    putUserProfile(userProfile: s.UserProfileDto): Action {
        return {
            type: PUT_USER_PROFILE,
            payload: userProfile,
            meta: "update userProfile in state"
        }
    }
}


export let actionCreater = new ActionCreater();