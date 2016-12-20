import { httpService } from "./HttpService"
import { store, AppState } from "../Store"
import { userProfileActionCreater } from "../actions/userProfile"


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

class AuthService {

    loadProfile() {
        httpService.get("me", (userProfileDto: UserProfileDto) => {
            userProfileActionCreater.putUserProfile(userProfileDto);
        }, (feedback) => {
            console.log("Error happened when getProfile:", feedback);
        });
    }

    login(userAuth: UserAuthDto) {
        httpService.put("me/auth", userAuth, this.loadProfile, (feedback) => {
            console.log("Error happened when putAuth:", feedback);
        });
    }

    logout() {
        httpService.delete("me/auth", this.loadProfile, (feedback) => {
            console.log("Error happened when deleteAuth:", feedback);
        });
    }

    isAdmin() {
        let state: AppState = store.getState();
        if (state.userProfile != null && state.userProfile.userType == "admin") {
            return true;
        }
        return false;
    }
}

export let authService = new AuthService();