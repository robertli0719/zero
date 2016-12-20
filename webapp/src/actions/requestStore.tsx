import { Action, ADD_REQUEST_RESULT, UPDATE_REQUEST_RESULT, DELETE_REQUEST_RESULT } from "../Store"
import { RequestResult } from "../reducers/requestStore"



class RequestStoreActionCreater {
    addRequestResult(key: string): Action {
        return {
            type: ADD_REQUEST_RESULT,
            payload: key,
            meta: "add requestResult in state"
        }
    }

    updateRequestResult(key: string, requestResult: RequestResult): Action {
        return {
            type: UPDATE_REQUEST_RESULT,
            payload: { key: key, requestResult: requestResult },
            meta: "update requestResult in state"
        }
    }

    deleteRequestResult(key: string): Action {
        return {
            type: DELETE_REQUEST_RESULT,
            meta: "delete requestResult in state"
        }
    }
}

export let requestStoreActionCreater = new RequestStoreActionCreater();