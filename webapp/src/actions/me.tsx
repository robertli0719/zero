import { http, RestErrorDto } from "../utilities/http"
import { Dispatch } from "redux"
import { store, AppState, Action, UPDATE_ME } from "../Store"
import * as forms from "../actions/forms"
import { FormState } from "../reducers/forms"
import { UserProfile } from "../reducers/me"
import { hashHistory } from 'react-router'

export type UserAuthDto = {
    userTypeName: string
    userPlatformName: string
    username: string
    password: string
}

export type UserProfileDto = {
    authLabel: string
    userTypeName: string
    userPlatformName: string
    name: string
    telephone: string
}

export function isAdmin() {
    let state: AppState = store.getState();
    if (state.me != null && state.me.userTypeName == "admin") {
        return true;
    }
    return false;
}

export function updateMe(userProfile: UserProfile): Action {
    return {
        type: UPDATE_ME,
        payload: userProfile,
        meta: "update userProfile in state"
    }
}

export function loadProfile() {
    return (dispatch: Dispatch<AppState>) => {
        return http.get("me")
            .then((userProfileDto: UserProfileDto) => {
                dispatch(updateMe(userProfileDto));
            })
            .catch((restError: RestErrorDto) => {
                console.log("Error happened when loadProfile:", restError);
                let nullUserProfile: UserProfile = { authLabel: null, userTypeName: null, userPlatformName: null, name: null, telephone: null }
                dispatch(updateMe(nullUserProfile));
                throw restError;
            });
    }
}

export function triggerLogin(userAuth: UserAuthDto, formId: string) {
    return (dispatch: Dispatch<AppState>, getState: () => AppState) => {
        if (forms.isProcessing(formId)) {
            return;
        }
        dispatch(forms.markFromAsProcessing(formId));
        return http.put("me/auth", userAuth)
            .then(() => {
                return dispatch(loadProfile());
            }).then(() => {
                dispatch(forms.unmarkFromAsProcessing(formId));
            }).catch((restError: RestErrorDto) => {
                let form: FormState = { processing: false, restError: restError }
                dispatch(forms.updateForm(formId, form));
                throw restError;
            });
    }
}

export function triggerLogout() {
    return (dispatch: Dispatch<AppState>) => {
        return http.delete("me/auth")
            .then(() => {
                return dispatch(loadProfile());
            })
            .catch((restError: RestErrorDto) => {
                document.cookie = 'access_token_p=; expires=Thu, 01-Jan-70 00:00:01 GMT;';
                console.log("Error happened when deleteAuth:", restError);
            });
    }
}