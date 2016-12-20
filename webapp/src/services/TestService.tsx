import { store } from "../Store"
import { testActionCreater } from "../actions/test"
import { Action } from "redux"

class TestService {

    addNumber(val: number) {
        let action: Action = testActionCreater.addNumber(val);
        store.dispatch(action);
    }
}

export let testService = new TestService();
