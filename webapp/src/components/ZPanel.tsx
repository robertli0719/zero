import * as React from "react"
import * as ReactDOM from "react-dom"
import { FormEvent } from "react"
import { Button, ButtonToolbar } from "react-bootstrap"
import { createStore, Action } from 'redux'
import { connect } from "react-redux"
import { testService } from "../services/TestService"
import { AppState } from "../Store"

interface InjectProps {
    val: number
}

interface ZPanelProps {
    title: string
}

type Props = InjectProps & ZPanelProps;

interface ZPanelState {
    objList: string[]
    itemName: string
    val: number
}

class ZPanelComponent extends React.Component<Props, ZPanelState>{

    constructor(props: Props) {
        super(props);
        this.state = {
            objList: ["apple", "pear", "orange"],
            itemName: "",
            val: this.props.val
        };
    }

    execute(name: string) {
        //alert("hello" + this.state.objList[0]);
        //console.log("val in store:" + store.getState().val);
    }

    add() {
        testService.addNumber(1);
    }

    render() {
        return (
            <div className="zPanel">
                <h1>{this.props.title}</h1>
                <p>x x xx x x x xxx s{this.state.itemName}: {this.props.val}</p>
                <ButtonToolbar>
                    <Button bsStyle="success" onClick={this.execute.bind(this)}>Show</Button>
                    <Button bsStyle="success" onClick={this.add.bind(this)}>Add</Button>
                </ButtonToolbar>
            </div>
        );
    }
}


function select(state: AppState): InjectProps {
    return { val: state.test.val };
}

export let ZPanel = connect(select)(ZPanelComponent) as React.ComponentClass<ZPanelProps>;