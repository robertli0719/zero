import { createStore, combineReducers, Store } from "redux"
import { testReducer, TestState } from "./reducers/test"
import { userProfileReducer, UserProfileState } from "./reducers/userProfile"
import { requestStoreReducer, RequestStoreState } from "./reducers/requestStore"

export const ADD_NUMBER = "ADD_NUMBER"
export const PUT_USER_PROFILE = "PUT_USER_PROFILE"
export const ADD_REQUEST_RESULT = "ADD_REQUEST_RESULT"
export const UPDATE_REQUEST_RESULT = "UPDATE_REQUEST_RESULT"
export const DELETE_REQUEST_RESULT = "DELETE_REQUEST_RESULT"

export interface Action {
    readonly type: string;
    readonly payload?: any;
    readonly meta?: string
}

export interface AppState {
    userProfile: UserProfileState
    test: TestState
    requestStore: RequestStoreState
}

const reducer = combineReducers<AppState>({
    userProfile: userProfileReducer,
    test: testReducer,
    requestStore: requestStoreReducer
})

export let store: Store<AppState> = createStore<AppState>(reducer);