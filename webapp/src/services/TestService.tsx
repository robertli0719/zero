import { store } from "../Store"
import { actionCreater } from "../ActionCreater"
import { Action } from "redux"

class TestService {

    addNumber(val: number) {
        let action: Action = actionCreater.addNumber(val);
        store.dispatch(action);
    }
}

export let testService = new TestService();
