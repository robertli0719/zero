import { http, RestErrorDto } from "../utilities/http"
import { store, AppState } from "../Store"
import { Dispatch } from "redux"
import { Auth } from "../reducers/auth"

export function initApp() {
    return () => {
        return http.put("admin/init", null)
            .then(() => {
                console.log("init success:");
            }).catch(error => {
                console.log("fail:", error)
            });
    }
}
// action test code below:
// 2016-12-26

// interface demoDto {
//     id: number;
//     name: string;
//     dateTime: Date;
// }

// let dto: demoDto = {
//     id: 123,
//     name: "demo abc",
//     dateTime: new Date()
// }

// http.get("test/demos")
//     .then((user: Auth) => {
//         console.log(user);
//     })
//     .catch((error: RestErrorDto) => {
//         console.log("catch:", error)
//     });

// http.post("test/demos", dto)
//     .then(() => {
//         console.log("put success..")
//     })
//     .catch((error: RestErrorDto) => {
//         console.log("error when POST:", error);
//     });
