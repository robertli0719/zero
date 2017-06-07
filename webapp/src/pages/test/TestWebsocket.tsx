import * as React from "react"
import * as ReactDOM from "react-dom"
import { store } from "../../Store"
import { Button, ButtonToolbar, FormControl, FormGroup, Col } from "react-bootstrap"
import { ImageUploadButton } from "../../components/zero/ImageUploadButton"

type State = {
    msg: string[]
}

export class TestWebsocket extends React.Component<{}, State>{

    constructor(props: {}) {
        super(props)
        this.state = { msg: [] }
    }

    log(str: string) {
        console.log(str)
        this.state.msg.push(str)
        this.setState(this.state)
    }

    connect() {
        const host = window.location.host
        this.log(host)
        const ws: WebSocket = new WebSocket("ws://" + host + "/Zero/chat")
        ws.onopen = () => {
            this.log("open")

        }

        ws.onmessage = (msg: MessageEvent) => {
            this.log("onmessage: " + msg.data)
        }

        ws.onerror = (error: any) => {
            console.log(error)
            this.log("Error: " + error)
        }

        ws.onclose = () => {
            this.log("onclose")
        }

        setInterval(() => {
            if (!ws.readyState) {
                return
            }
            ws.send("hello,world~! " + (new Date()))
        }, 10000)
    }

    run() {
        this.connect()
    }

    render() {
        return (
            <div className="container">
                <p>Websocket Test</p>
                <Button onClick={this.run.bind(this)}>Run</Button>
                <ScrollView lines={this.state.msg} />
            </div>
        )
    }
}

type ScrollViewProps = {
    lines: string[]
}

class ScrollView extends React.Component<ScrollViewProps, {}>{

    constructor(props: ScrollViewProps) {
        super(props)
    }

    componentDidUpdate() {
        ReactDOM.findDOMNode(this).scrollTop = ReactDOM.findDOMNode(this).scrollHeight
    }

    render() {
        return <div style={{ height: "200px", overflow: "scroll", backgroundColor: "#ccc" }}>
            {
                this.props.lines.map((line: string, index: number) => {
                    return <p>{index} -> {line}</p>
                })
            }
        </div>
    }
}