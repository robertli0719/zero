import { Action, UPDATE_FORM, DELETE_FORM, MARK_FROM_AS_PROCESSING, UNMARK_FROM_AS_PROCESSING, store } from "../Store"
import { FormState } from "../reducers/forms"

export function isProcessing(formId: string) {
    let from: FormState = store.getState().forms[formId];
    if (from != null) {
        return from.processing;
    }
    return false;
}

export function markFromAsProcessing(formId: string) {
    return {
        type: MARK_FROM_AS_PROCESSING,
        payload: formId,
        meta: "mark form as processing in state"
    }
}

export function unmarkFromAsProcessing(formId: string) {
    return {
        type: UNMARK_FROM_AS_PROCESSING,
        payload: formId,
        meta: "unmark form as processing in state"
    }
}

export function updateForm(formId: string, form: FormState): Action {
    return {
        type: UPDATE_FORM,
        payload: { formId: formId, form: form },
        meta: "update form in state"
    }
}

export function deleteForm(formId: string): Action {
    return {
        type: DELETE_FORM,
        payload: formId,
        meta: "delete form in state"
    }
}


