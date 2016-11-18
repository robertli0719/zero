import * as React from "react";
import * as ReactDOM from "react-dom";
import { Provider } from "react-redux";
import { Router, Route, hashHistory, IndexRoute } from 'react-router';
import { store } from "./Store"
import { AppNavbar } from "./components/zero/AppNavbar"
import { Index } from "./pages/Index"
import { About } from "./pages/About"
import * as jQuery from "jquery";


class App extends React.Component<{}, {}>{

    constructor() {
        super();
        console.log("hello,world App");
    }

    render() {
        return (
            <div>
                <AppNavbar />
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
                <Route path="/index" component={Index} />
                <Route path="/about" component={About} />
            </Route>
        </Router>
    </Provider>
);

ReactDOM.render(template, document.getElementById("context"));