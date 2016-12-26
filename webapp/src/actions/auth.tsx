import { http, RestErrorDto } from "../utilities/http"
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
        return http.get("me")
            .then((userProfileDto: UserProfileDto) => {
                dispatch(updateAuth(userProfileDto));
            })
            .catch((restError: RestErrorDto) => {
                console.log("Error happened when loadProfile:", restError);
                let nullAuth: UserProfileDto = { authLabel: null, userType: null, name: null, telephone: null }
                dispatch(updateAuth(nullAuth));
                throw restError;
            });
    }
}

export function triggerLogin(userAuth: UserAuthDto, formId: string) {
    return (dispatch: Dispatch<AppState>, getState: () => AppState) => {
        let form: FormState = getState().forms[formId];
        if (form != null && form.processing) {
            return;
        }
        dispatch(froms.markFromAsProcessing(formId));
        return http.put("me/auth", userAuth)
            .then(() => {
                dispatch(loadProfile()).then(() => {
                    dispatch(froms.unmarkFromAsProcessing(formId));
                })
            }).catch((restError: RestErrorDto) => {
                let form: FormState = { processing: false, restError: restError }
                dispatch(froms.updateForm(formId, form));
            });
    }
}

export function triggerLogout() {
    return (dispatch: Dispatch<AppState>) => {
        return http.delete("me/auth")
            .then(() => {
                dispatch(loadProfile()).then(() => {
                });
            })
            .catch((restError: RestErrorDto) => {
                console.log("Error happened when deleteAuth:", restError);
            });
    }
}