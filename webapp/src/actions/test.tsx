import { Action, ADD_NUMBER } from "../Store"

export function addNumber(val: number): Action {
    return {
        type: ADD_NUMBER,
        payload: val,
        meta: "increasse the number in state"
    }
}

export function demo() {
    console.log("in demo")
    return () => {
        console.log("middleware")
    }
}