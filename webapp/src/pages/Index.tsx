import * as React from "react";
import * as ReactDOM from "react-dom";
import { Button, ButtonToolbar } from "react-bootstrap";
import { ZPanel } from "../components/ZPanel"

interface IndexStateModel {
    num: number
    name: string
}

export class Index extends React.Component<{}, IndexStateModel>{

    constructor() {
        super();
        this.state = { name: "hello", num: 0 };
    }

    render() {
        return (
            <div className="container">
                <ButtonToolbar>
                    <Button>hello</Button>
                    <Button>hello</Button>
                    <Button>hello</Button>
                    <Button bsStyle="success">hello</Button>
                </ButtonToolbar>
                <ZPanel title="Panel-1" />
                <ZPanel title="Panel-2" />
            </div>
        );
    }
}
