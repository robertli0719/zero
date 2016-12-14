import * as React from "react";
import * as ReactDOM from "react-dom";
import { Button, ButtonToolbar } from "react-bootstrap";
import { AppService } from "../../services/AppService"
import { AuthService, UserAuthDto } from "../../services/AuthService"

export class AppInit extends React.Component<{}, {}>{

    constructor() {
        super();
        console.log("hello, world~! About");
    }

    appInit() {
        AppService.getInstance().initApp();
    }

    test() {
        let userAuth: UserAuthDto = {
            userType: "general",
            platform: null,
            username: "tom",
            password: "hello123"
        };
    }

    testLogout() {
    }

    render() {
        return (
            <div className="container">
                <h1>App Init 2</h1>
                <ButtonToolbar>
                    <Button bsStyle="success" onClick={this.appInit.bind(this)}>Init</Button>
                    <Button bsStyle="success" onClick={this.test.bind(this)}>Test</Button>
                    <Button bsStyle="success" onClick={this.testLogout.bind(this)}>testLogout</Button>
                </ButtonToolbar>
            </div>
        );
    }
}
