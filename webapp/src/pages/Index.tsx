import * as React from "react"
import * as ReactDOM from "react-dom"
import { Button, ButtonToolbar } from "react-bootstrap"

interface IndexState {
    num: number
    name: string
    items: string[]
}

export class Index extends React.Component<{}, IndexState>{

    constructor() {
        super();
        //there are two variable in the state.
        this.state = { name: "hello", num: 0, items: [] };
    }

    fun1() {
        //For this.state.num++;
        this.setState({ num: this.state.num + 1 })
    }


    fun2() {
        // For reset this.state.name='tom'
        this.setState({ name: "tom" })
    }


    fun3() {
        this.state.items.push("monkey")
        this.setState({})
        console.log(this.state)
    }

    render() {
        return (
            <div className="container">
                <p>num:{this.state.num}</p>
                <p>name:{this.state.name}</p>
                <ul>
                    {this.state.items.map((item) => {
                        return <li>{item}</li>
                    })}
                </ul>
                <ButtonToolbar>
                    <Button onClick={this.fun1.bind(this)}>fun1</Button>
                    <Button onClick={this.fun2.bind(this)}>fun2</Button>
                    <Button onClick={this.fun3.bind(this)}>fun3</Button>
                    <Button bsStyle="success">hello</Button>
                </ButtonToolbar>
            </div>
        )
    }
}
