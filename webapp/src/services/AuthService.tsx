import { httpService } from "./HttpService"
import { UserProfileDto, UserAuthDto, store, AppState } from "../Store"
import { actionCreater } from "../ActionCreater"


class AuthService {

    loadProfile() {
        httpService.get("me", (userProfileDto: UserProfileDto) => {
            actionCreater.putUserProfile(userProfileDto);
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

    idAdmin() {
        let state: AppState = store.getState();
        if (state.userProfile != null && state.userProfile.userType == "admin") {
            return true;
        }
        return false;
    }
}

export let authService = new AuthService();