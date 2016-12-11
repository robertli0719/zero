
export class AppService {

    private static instance = new AppService()

    private constructor() { }

    public static getInstance(): AppService {
        return this.instance;
    }

    initApp() {
        $.ajax({
            url: "admin/init",
            method: "put",
            success: (feedback) => {
                console.log("init success:", feedback);
            },
            error: (feedback) => {
                console.log("error happened:", feedback);
            }
        });
    }
}