import { createStore, applyMiddleware, combineReducers, Store } from "redux"
import thunkMiddleware from "redux-thunk"
import { testReducer, TestState } from "./reducers/test"
import { meReducer, UserProfile } from "./reducers/me"

export const UPDATE_FORM = "UPDATE_FORM"
export const DELETE_FORM = "DELETE_FORM"
export const UPDATE_ME = "UPDATE_ME"
export const ADD_NUMBER = "ADD_NUMBER"
export const MARK_FROM_AS_PROCESSING = "MARK_FROM_AS_PROCESSING"
export const UNMARK_FROM_AS_PROCESSING = "UNMARK_FROM_AS_PROCESSING"

export interface Action {
    readonly type: string
    readonly payload?: any
    readonly meta?: string
}

export interface AppState {
    me: UserProfile
    test: TestState
}

const reducer = combineReducers<AppState>({
    me: meReducer,
    test: testReducer
})

export const store: Store<AppState> = createStore<AppState>(reducer, applyMiddleware(thunkMiddleware))