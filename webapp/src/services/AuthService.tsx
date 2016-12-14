import { HttpService } from "./HttpService"

export type UserAuthDto = {
    userType: string
    platform: string
    username: string
    password: string
}

export type UserProfileDto = {
    authLabel: string
    userType: string
    name: string
    telephone: string
}

export class AuthService {

    private static instance = new AuthService();

    private httpService = HttpService.getInstance();

    private AuthService() {
    }

    public static getInstance(): AuthService {
        return this.instance;
    }

    getProfile(callback: (userProfileDto: UserProfileDto) => any) {
        this.httpService.get("me", callback, (feedback) => {
            console.log("Error happened when getProfile:", feedback);
        });
    }

    putAuth(userAuth: UserAuthDto, callback: () => any) {
        this.httpService.put("me/auth", userAuth, callback, (feedback) => {
            console.log("Error happened when putAuth:", feedback);
        });
    }

    deleteAuth(callback: () => any) {
        this.httpService.delete("me/auth", callback, (feedback) => {
            console.log("Error happened when deleteAuth:", feedback);
        });
    }
}