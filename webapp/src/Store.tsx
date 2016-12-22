import { createStore, applyMiddleware, combineReducers, Store } from "redux"
import thunkMiddleware from "redux-thunk"
import { testReducer, TestState } from "./reducers/test"
import { authReducer, Auth } from "./reducers/auth"
import { formsReducer, Forms } from "./reducers/forms"

export const ADD_NUMBER = "ADD_NUMBER"
export const UPDATE_AUTH = "UPDATE_AUTH"
export const UPDATE_FORM = "UPDATE_FORM"
export const DELETE_FORM = "DELETE_FORM"
export const MARK_FROM_AS_PROCESSING = "MARK_FROM_AS_PROCESSING"
export const UNMARK_FROM_AS_PROCESSING = "UNMARK_FROM_AS_PROCESSING"

export interface Action {
    readonly type: string;
    readonly payload?: any;
    readonly meta?: string
}

export interface AppState {
    auth: Auth
    forms: Forms
    test: TestState
}

const reducer = combineReducers<AppState>({
    auth: authReducer,
    forms: formsReducer,
    test: testReducer
})

export let store: Store<AppState> = createStore<AppState>(reducer, applyMiddleware(thunkMiddleware));