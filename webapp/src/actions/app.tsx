import { http } from "../utilities/http"

export function initApp() {
    return () => {
        http.put("admin/init", null, (feedback) => {
            console.log("success:", feedback);
        }, (result) => {
            console.log("fail:", result);
        });
    }
}