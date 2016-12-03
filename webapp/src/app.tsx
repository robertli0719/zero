import * as React from "react";
import * as ReactDOM from "react-dom";
import { Provider } from "react-redux";
import { Router, Route, hashHistory, IndexRoute } from 'react-router';
import { store } from "./Store"
import { AppNavbar, NavbarItem } from "./components/zero/AppNavbar"
import { Index } from "./pages/Index"
import { About } from "./pages/About"
import { Test } from "./pages/Test"
import { UserRegister } from "./pages/auth/UserRegister"
import { UserRegisterVerifiy } from "./pages/auth/UserRegisterVerifiy"
import { UserLogin } from "./pages/auth/UserLogin"

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
            </Route>
        </Router>
    </Provider>
);

ReactDOM.render(template, document.getElementById("context"));