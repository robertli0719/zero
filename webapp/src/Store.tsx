import { createStore, Action } from "redux"



export interface AppState {
    val: number
}

let initState: AppState = { val: 0 }

function reducer(state: any = initState, action: Action): any {
    switch (action.type) {
        case "ADD_NUMBER":
            return { val: state.val + 1 };
        default:
            return state;
    }

}

export let store = createStore(reducer);