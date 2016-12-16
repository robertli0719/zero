import * as React from "react";
import * as ReactDOM from "react-dom";
import { Button, ButtonToolbar } from "react-bootstrap";
import { appService } from "../../services/AppService"
import { authService } from "../../services/AuthService"
import { UserAuthDto } from "../../Store"

export class AppInit extends React.Component<{}, {}>{

    constructor() {
        super();
    }

    appInit() {
        appService.initApp();
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
