import { httpService } from "./HttpService"
import { UserProfileDto, UserAuthDto } from "../Store"


class AuthService {

    private static instance = new AuthService();

    private AuthService() {
    }

    public static getInstance(): AuthService {
        return this.instance;
    }

    getProfile(callback: (userProfileDto: UserProfileDto) => any) {
        httpService.get("me", callback, (feedback) => {
            console.log("Error happened when getProfile:", feedback);
        });
    }

    putAuth(userAuth: UserAuthDto, callback: () => any) {
        httpService.put("me/auth", userAuth, callback, (feedback) => {
            console.log("Error happened when putAuth:", feedback);
        });
    }

    deleteAuth(callback: () => any) {
        httpService.delete("me/auth", callback, (feedback) => {
            console.log("Error happened when deleteAuth:", feedback);
        });
    }
}

export let authService = new AuthService();