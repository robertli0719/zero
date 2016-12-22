import { Action, UPDATE_FORM, DELETE_FORM, MARK_FROM_AS_PROCESSING, UNMARK_FROM_AS_PROCESSING, store } from "../Store"
import { FormState } from "../reducers/forms"

export function isProcessing(fromId: string) {
    let from: FormState = store.getState().forms[fromId];
    if (from != null) {
        return from.processing;
    }
    return false;
}

export function markFromAsProcessing(fromId: string) {
    return {
        type: MARK_FROM_AS_PROCESSING,
        payload: fromId,
        meta: "mark form as processing in state"
    }
}

export function unmarkFromAsProcessing(fromId: string) {
    return {
        type: UNMARK_FROM_AS_PROCESSING,
        payload: fromId,
        meta: "unmark form as processing in state"
    }
}

export function updateForm(fromId: string, form: FormState): Action {
    return {
        type: UPDATE_FORM,
        payload: { fromId: fromId, form: form },
        meta: "update form in state"
    }
}

export function deleteForm(fromId: string): Action {
    return {
        type: DELETE_FORM,
        payload: fromId,
        meta: "delete form in state"
    }
}


