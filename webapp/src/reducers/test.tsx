import { Action, ADD_NUMBER } from "../Store"

export interface TestState {
    val: number
}

let initState: TestState = {
    val: 0
}

export function testReducer(state: TestState = initState, action: Action): any {
    switch (action.type) {
        case ADD_NUMBER:
            state = $.extend({}, state);
            state.val = state.val + 1;
            return state;
        default:
            return state;
    }
}