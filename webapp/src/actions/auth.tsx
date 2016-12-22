import { http } from "../utilities/http"
import { Dispatch } from "redux"
import { store, AppState, Action, UPDATE_AUTH } from "../Store"
import * as froms from "../actions/forms"
import { FormState } from "../reducers/forms"

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

export function isAdmin() {
    let state: AppState = store.getState();
    if (state.auth != null && state.auth.userType == "admin") {
        return true;
    }
    return false;
}

export function updateAuth(userProfile: UserProfileDto): Action {
    return {
        type: UPDATE_AUTH,
        payload: userProfile,
        meta: "update userProfile in state"
    }
}

export function loadProfile() {
    return (dispatch: Dispatch<AppState>) => {
        http.get("me", (userProfileDto: UserProfileDto) => {
            dispatch(updateAuth(userProfileDto));
        }, (feedback) => {
            console.log("Error happened when loadProfile:", feedback);
            let nullAuth: UserProfileDto = { authLabel: null, userType: null, name: null, telephone: null }
            dispatch(updateAuth(nullAuth));
        });
    }
}

export function triggerLogin(userAuth: UserAuthDto, formId: string) {
    return () => {
        let form: FormState = store.getState().forms[formId];
        if (form != null && form.processing) {
            return;
        }
        // form.processing
        // unfinish
        // let action: Action = requestStoreActionCreater.addRequestResult(formId);
        // store.dispatch(action);
        http.put("me/auth", userAuth, this.loadProfile, (feedback) => {
            console.log("Error happened when putAuth:", feedback);
        });
    }
}

export function triggerLogout() {
    return () => {
        http.delete("me/auth", this.loadProfile, (feedback) => {
            console.log("Error happened when deleteAuth:", feedback);
        });
    }
}