import * as React from "react";
import { hashHistory } from "react-router"
import { AuthService } from "../../services/AuthService"


export class AdminInterceptor extends React.Component<{}, {}>{

    constructor() {
        super();
        console.log("AdminInterceptor");
    }

    componentWillMount() {
        console.log("componentWillMount");
        let authService = AuthService.getInstance();

        authService.getProfile((profile) => {
            console.log("current UserType:" + profile.userType);
            let pathname = hashHistory.getCurrentLocation().pathname;
            console.log(pathname);
            if (pathname !== '/admin/login') {
                hashHistory.push('/admin/login')
            }
        });
    }
    render() {
        return (
            <div>{this.props.children}</div>
        )
    }
}