import { Action, UPDATE_FORM, DELETE_FORM, MARK_FROM_AS_PROCESSING, UNMARK_FROM_AS_PROCESSING } from "../Store"
import { RestErrorDto } from "../utilities/http"

export type FormState = {
    processing: boolean
    restError: RestErrorDto
}

export interface Forms {
    [key: string]: FormState
}

let initState: Forms = {}

export function formsReducer(state: Forms = initState, action: Action): any {
    switch (action.type) {
        case UPDATE_FORM:
            state = $.extend({}, state);
            state[action.payload.formId] = action.payload.form;
            return state;
        case DELETE_FORM:
            state = $.extend({}, state);
            delete state[action.payload];
            return state;
        case MARK_FROM_AS_PROCESSING:
            let formId = action.payload;
            state = $.extend({}, state);
            let fromM = state[formId];
            if (fromM == null) {
                state[formId] = { processing: true, restError: null };
            } else {
                state[formId] = $.extend({}, fromM, { processing: true });
            }
            return state;
        case UNMARK_FROM_AS_PROCESSING:
            formId = action.payload;
            state = $.extend({}, state);
            let fromUM = state[formId];
            if (fromUM == null) {
                state[formId] = { processing: false, restError: null };
            } else {
                state[formId] = $.extend({}, fromUM, { processing: false });
            }
            return state;
        default:
            return state;
    }
}