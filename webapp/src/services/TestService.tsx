import { store } from "../Store"
import { Action } from "redux"

class TestService {

    addNumber(val: number) {
        let action: Action = createAddNumberAction(val);
        store.dispatch(action);
    }
}

function createAddNumberAction(val: number) {
    return {
        type: "ADD_NUMBER",
        payload: val,
        meta: "increasse the number in state"
    }
}

export let testService = new TestService();
