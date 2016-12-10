

export class AuthService {

    private static instance = new AuthService();

    private AuthService() {
    }

    public static getInstance(): AuthService {
        return this.instance;
    }

    getProfile() {
        $.ajax({
            url: "me",
            method: "get",
            success: (feedback) => {
                console.log("getProfile success:", feedback);
            },
            error: (feedback) => {
                console.log("Error happened:", feedback);
            }
        });
    }
}