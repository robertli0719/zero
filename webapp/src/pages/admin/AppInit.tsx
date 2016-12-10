import * as React from "react";
import * as ReactDOM from "react-dom";
import { Button, ButtonToolbar } from "react-bootstrap";

export class AppInit extends React.Component<{}, {}>{

    constructor() {
        super();
        console.log("hello, world~! About");
    }

    appInit() {
        $.ajax({
            url: "admin/init",
            method: "put",
            success: (feedback) => {
                console.log("init success:", feedback);
            },
            error: (feedback) => {
                console.log("error happened:", feedback);
            }
        });
    }

    render() {
        return (
            <div className="container">
                <h1>App Init</h1>
                <ButtonToolbar>
                    <Button bsStyle="success" onClick={this.appInit.bind(this)}>Init</Button>
                </ButtonToolbar>
            </div>
        );
    }
}
