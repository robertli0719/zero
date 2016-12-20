import { Action, PUT_USER_PROFILE } from "../Store"
import { UserProfileState } from "../reducers/UserProfile"

class UserProfileActionCreater {

    putUserProfile(userProfile: UserProfileState): Action {
        return {
            type: PUT_USER_PROFILE,
            payload: userProfile,
            meta: "update userProfile in state"
        }
    }

}

export let userProfileActionCreater = new UserProfileActionCreater();