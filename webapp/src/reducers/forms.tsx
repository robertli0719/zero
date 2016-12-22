import { Action, UPDATE_FORM, DELETE_FORM, MARK_FROM_AS_PROCESSING, UNMARK_FROM_AS_PROCESSING } from "../Store"

export type RestError = {
    type: string
    source: string
    message: string
    detail: string
}

export type FormState = {
    processing: boolean
    restError: RestError
}

export interface Forms {
    [key: string]: FormState
}

let initState: Forms = {}

export function formsReducer(state: Forms = initState, action: Action): any {
    switch (action.type) {
        case UPDATE_FORM:
            state = $.extend({}, state);
            state[action.payload.fromId] = action.payload.form;
            return state;
        case DELETE_FORM:
            state = $.extend({}, state);
            delete state[action.payload];
            return state;
        case MARK_FROM_AS_PROCESSING:
            state = $.extend({}, state);
            let fromM = state[action.payload.fromId];
            if (fromM == null) {
                state[action.payload.fromId] = { processing: true, restError: null };
            } else {
                state[action.payload.fromId] = $.extend({}, fromM, { processing: true });
            }
            return state;
        case UNMARK_FROM_AS_PROCESSING:
            state = $.extend({}, state);
            let fromUM = state[action.payload.fromId];
            if (fromUM == null) {
                state[action.payload.fromId] = { processing: false, restError: null };
            } else {
                state[action.payload.fromId] = $.extend({}, fromUM, { processing: false });
            }
            return state;
        default:
            return state;
    }
}