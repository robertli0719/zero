import * as React from "react";
import { hashHistory } from "react-router"
import { authService } from "../../services/AuthService"

class AdminInterceptorState {
    active: boolean
}

export class AdminInterceptor extends React.Component<{}, AdminInterceptorState>{

    constructor() {
        super();
        this.state = { active: true };
    }

    componentWillMount() {
        // let authService = AuthService.getInstance();

        // authService.getProfile((profile) => {
        //     console.log("current UserType:" + profile.userType);
        //     let pathname = hashHistory.getCurrentLocation().pathname;
        //     console.log(pathname);
        //     if (pathname !== '/admin/login') {
        //         this.state.active = true;
        //         this.setState(this.state);
        //         console.log("before push");
        //         hashHistory.push('/admin/login');
        //         console.log("after push");
        //     }
        // });
    }
    render() {
        let result = <div className="container"><h1>Loading...</h1></div>;
        if (this.state.active) {
            result = <div>{this.props.children}</div>;
        }
        return result;
    }
}