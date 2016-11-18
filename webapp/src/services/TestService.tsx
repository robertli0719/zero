import { store } from "../Store"
import { Action } from "redux"

export class TestService {

    private static instance = new TestService()

    public static getInstance() {
        return this.instance;
    }

    private constructor() {
    }

    addNumber(val:number) {
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

