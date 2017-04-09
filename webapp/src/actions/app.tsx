import { http, RestErrorDto } from "../utilities/http"
import { store, AppState } from "../Store"
import { Dispatch } from "redux"
import { UserProfile } from "../reducers/me"

export function initApp() {
    return () => {
        return http.put("app/init", null)
            .then(() => {
                console.log("init success:")
            }).catch(error => {
                console.log("fail:", error)
            })
    }
}
