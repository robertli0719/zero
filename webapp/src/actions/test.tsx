import { Action, ADD_NUMBER } from "../Store"


class TestActionCreater {
    addNumber(val: number): Action {
        return {
            type: ADD_NUMBER,
            payload: val,
            meta: "increasse the number in state"
        }
    }
}

export let testActionCreater = new TestActionCreater();