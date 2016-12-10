import * as React from "react";
import * as ReactDOM from "react-dom";
import { Provider } from "react-redux";
import { Router, Route, hashHistory, IndexRoute } from 'react-router';
import { store } from "./Store"
import { AuthService } from "./services/AuthService"
import { AppNavbar, NavbarItem } from "./components/zero/AppNavbar"
import { Index } from "./pages/Index"
import { About } from "./pages/About"
import { Test } from "./pages/Test"
import { UserRegister } from "./pages/auth/UserRegister"
import { UserRegisterVerifiy } from "./pages/auth/UserRegisterVerifiy"
import { UserLogin } from "./pages/auth/UserLogin"
import { AppInit } from "./pages/admin/AppInit"
import { AdminIndex } from "./pages/admin/AdminIndex"
import { AdminLogin } from "./pages/admin/AdminLogin"


let navBarItemList = [
    { name: "Index", url: "/index" },
    { name: "About", url: "/about" },
    { name: "Test", url: "/test" }
];

let rightNavBarItemList = [
    // { name: "Link", url: "/" },
    // { name: "Link", url: "/" }
] as NavbarItem[];

class App extends React.Component<{}, {}>{

    constructor() {
        super();
    }

    render() {
        return (
            <div>
                <AppNavbar name="React-Bootstrap" itemList={navBarItemList} rightItemList={rightNavBarItemList} />
                {this.props.children}
            </div>
        )
    }
}

class AdminInterceptor extends React.Component<{}, {}>{

    constructor() {
        super();
        AuthService.getInstance().getProfile();
        console.log("AdminInterceptor");
    }
    render() {
        return (
            <div>{this.props.children}</div>
        )
    }
}

let template = (
    <Provider store={store}>
        <Router history={hashHistory}>
            <Route path="/" component={App}>
                <IndexRoute component={Index} />
                <Route path="index" component={Index} />
                <Route path="about" component={About} />
                <Route path="test" component={Test} />
                <Route path="auth">
                    <Route path="register" component={UserRegister} />
                    <Route path="register/verifiy/:code" component={UserRegisterVerifiy} />
                    <Route path="login" component={UserLogin} />
                </Route>
                <Route path="admin" component={AdminInterceptor}>
                    <Route path="init" component={AppInit} />
                    <Route path="index" component={AdminIndex} />
                    <Route path="login" component={AdminLogin} />
                </Route>
            </Route>
        </Router>
    </Provider>
);

ReactDOM.render(template, document.getElementById("context"));