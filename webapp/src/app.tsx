import * as React from "react"
import * as ReactDOM from "react-dom"
import { Provider } from "react-redux"
import { Router, Route, hashHistory, IndexRoute, RouterState, RedirectFunction } from 'react-router'
import { store, AppState } from "./Store"
import { AppNavbar, NavbarItem } from "./components/zero/AppNavbar"
import * as me from "./actions/me"

// -- Pages --
// defalt pages
import { App } from "./pages/App"
import { Index } from "./pages/Index"
import { About } from "./pages/About"

//dashboard pages
import { InventoryView } from "./pages/dashboard/InventoryView"
import { ReportView } from "./pages/dashboard/ReportView"
import { StaffApp } from "./pages/dashboard/StaffApp"
import { StaffIndex } from "./pages/dashboard/StaffIndex"
import { StaffLogin } from "./pages/dashboard/StaffLogin"

//auth pages
import { Me } from "./pages/auth/Me"
import { Login } from "./pages/auth/Login"
import { Register } from "./pages/auth/Register"
import { RegisterVerify } from "./pages/auth/RegisterVerify"

//admin pages
import { AdminApp } from "./pages/admin/AdminApp"
import { AppInit } from "./pages/admin/AppInit"
import { AdminLogin } from "./pages/admin/AdminLogin"
import { AdminIndex } from "./pages/admin/AdminIndex"
import { AdminUserAdmin } from "./pages/admin/AdminUserAdmin"
import { AdminUserManagement } from "./pages/admin/AdminUserManagement"
import { AdminUserRole } from "./pages/admin/AdminUserRole"
import { AdminUserPlatform } from "./pages/admin/AdminUserPlatform"
import { AdminUserStaff } from "./pages/admin/AdminUserStaff"


//test pages
import { TestApp } from "./pages/test/TestApp"
import { TestIndex } from "./pages/test/TestIndex"
import { TestCropper } from "./pages/test/TestCropper"
import { TestForm } from "./pages/test/TestForm"
import { TestImageUpload } from "./pages/test/TestImageUpload"
import { TestCros } from "./pages/test/TestCros"
import { TestWebsocket } from "./pages/test/TestWebsocket"

function requireRoleAdmin(nextState: RouterState, replace: RedirectFunction) {
    const loginPath = '/admin/login'
    if (me.isAdmin() == false) {
        replace({
            pathname: loginPath
        })
    }
}

function requireRoleStaff(nextState: RouterState, replace: RedirectFunction) {
    const platformName = nextState.params["platform"]
    const loginPath = '/dashboard/' + platformName + "/login"
    if (me.isPlatformUser(platformName) == false) {
        replace({
            pathname: loginPath
        })
    }
}

const template = (
    <Provider store={store}>
        <Router history={hashHistory}>
            <Route path="/" >
                <Route component={App}>
                    <IndexRoute component={Index} />
                    <Route path="index" component={Index} />
                    <Route path="about" component={About} />
                </Route>
                <Route path="auth">
                    <IndexRoute component={Me} />
                    <Route path="me" component={Me} />
                    <Route path="login" component={Login} />
                    <Route path="register" component={Register} />
                    <Route path="register/verify/:code" component={RegisterVerify} />
                </Route>
                <Route path="dashboard/:platform" component={StaffApp}>
                    <Route path="index" component={StaffIndex} onEnter={requireRoleStaff} />
                    <Route path="inventory" component={InventoryView} onEnter={requireRoleStaff} />
                    <Route path="report" component={ReportView} onEnter={requireRoleStaff} />
                    <Route path="login" component={StaffLogin} />
                </Route>
                <Route path="admin" component={AdminApp}>
                    <Route path="init" component={AppInit} />
                    <Route path="index" component={AdminIndex} onEnter={requireRoleAdmin} />
                    <Route path="user" component={AdminUserManagement} onEnter={requireRoleAdmin} />
                    <Route path="user-admin" component={AdminUserAdmin} onEnter={requireRoleAdmin} />
                    <Route path="user-staff/:platform" component={AdminUserStaff} onEnter={requireRoleAdmin} />
                    <Route path="user-platform" component={AdminUserPlatform} onEnter={requireRoleAdmin} />
                    <Route path="user-role" component={AdminUserRole} onEnter={requireRoleAdmin} />
                    <Route path="login" component={AdminLogin} />
                </Route>
                <Route path="test" component={TestApp}>
                    <Route path="index" component={TestIndex} />
                    <Route path="cropper" component={TestCropper} />
                    <Route path="form" component={TestForm} />
                    <Route path="image-upload" component={TestImageUpload} />
                    <Route path="cros" component={TestCros} />
                    <Route path="websocket" component={TestWebsocket} />
                </Route>
            </Route>
        </Router>
    </Provider>
)

ReactDOM.render(template, document.getElementById("context"))