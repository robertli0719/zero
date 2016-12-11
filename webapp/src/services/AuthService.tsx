
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

let a: UserAuthDto

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
                let userProfile: UserProfileDto = feedback;

                console.log("getProfileDto success:", userProfile);
                console.log("name:", userProfile.name);
            },
            error: (feedback) => {
                console.log("Error happened:", feedback);
            }
        });
    }

    putAuth(userAuth: UserAuthDto) {
        let json = JSON.stringify(userAuth);
        $.ajax({
            url: "me/auth",
            method: "put",
            contentType: "application/json;charset=UTF-8",
            data: json,
            success: (feedback) => {
                console.log("init success:", feedback);
            },
            error: (feedback) => {
                console.log("error happened:", feedback);
            }
        });
    }

    deleteAuth(){
        $.ajax({
            url: "me/auth",
            method: "delete",
            contentType: "application/json;charset=UTF-8",
            success: (feedback) => {
                console.log("deleteAuth success:", feedback);
            },
            error: (feedback) => {
                console.log("error happened:", feedback);
            }
        });
    }
}