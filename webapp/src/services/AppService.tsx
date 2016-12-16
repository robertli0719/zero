import { httpService } from "./HttpService"

class AppService {

    initApp() {
        httpService.put("admin/init", null, (feedback) => {
            console.log("success:", feedback);
        }, (result) => {
            console.log("fail:", result);
        });
    }
}

export let appService = new AppService();