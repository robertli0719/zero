import * as React from "react";
import * as ReactDOM from "react-dom";
import { Provider } from "react-redux";
import { Router, Route, hashHistory, IndexRoute, RouterState, RedirectFunction } from 'react-router';
import { store, AppState } from "./Store"
import { AppNavbar, NavbarItem } from "./components/zero/AppNavbar"
import * as auth from "./actions/auth"
import { Index } from "./pages/Index"
import { About } from "./pages/About"
import { Test } from "./pages/Test"
import { UserRegister } from "./pages/auth/UserRegister"
import { UserRegisterVerifiy } from "./pages/auth/UserRegisterVerifiy"
import { UserLogin } from "./pages/auth/UserLogin"
import { InventoryView } from "./pages/dashboard/InventoryView"
import { ReportView } from "./pages/dashboard/ReportView"
import { StaffApp } from "./pages/dashboard/StaffApp"
import { StaffIndex } from "./pages/dashboard/StaffIndex"
import { StaffLogin } from "./pages/dashboard/StaffLogin"
import { AdminApp } from "./pages/admin/AdminApp"
import { AppInit } from "./pages/admin/AppInit"
import { AdminLogin } from "./pages/admin/AdminLogin"
import { AdminIndex } from "./pages/admin/AdminIndex"
import { AdminUserManagement } from "./pages/admin/AdminUserManagement"


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
                <AppNavbar name="React-Bootstrap" brandUrl="/" itemList={navBarItemList} rightItemList={rightNavBarItemList} />
                {this.props.children}
            </div>
        )
    }
}

function requireRoleAdmin(nextState: RouterState, replace: RedirectFunction) {
    let path = hashHistory.getCurrentLocation().pathname;
    let loginPath = '/admin/login';
    if (auth.isAdmin() == false) {
        replace({
            pathname: loginPath
        })
    }
}

let template = (
    <Provider store={store}>
        <Router history={hashHistory}>
            <Route path="/" >
                <Route component={App}>
                    <IndexRoute component={Index} />
                    <Route path="index" component={Index} />
                    <Route path="about" component={About} />
                    <Route path="test" component={Test} />
                    <Route path="auth">
                        <Route path="register" component={UserRegister} />
                        <Route path="register/verifiy/:code" component={UserRegisterVerifiy} />
                        <Route path="login" component={UserLogin} />
                    </Route>
                </Route>
                <Route path="dashboard/:platform" component={StaffApp}>
                    <Route path="index" component={StaffIndex} />
                    <Route path="inventory" component={InventoryView} />
                    <Route path="report" component={ReportView} />
                    <Route path="login" component={StaffLogin} />
                </Route>
                <Route path="admin" component={AdminApp}>
                    <Route path="init" component={AppInit} />
                    <Route path="index" component={AdminIndex} onEnter={requireRoleAdmin} />
                    <Route path="user" component={AdminUserManagement} onEnter={requireRoleAdmin} />
                    <Route path="login" component={AdminLogin} />
                </Route>
            </Route>
        </Router>
    </Provider>
);

ReactDOM.render(template, document.getElementById("context"));