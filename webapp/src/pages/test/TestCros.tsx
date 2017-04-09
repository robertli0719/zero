import * as React from "react"
import * as ReactDOM from "react-dom"
import { store } from "../../Store"
import { Button, ButtonToolbar, FormControl, FormGroup, Col } from "react-bootstrap"
import { ImageUploadButton } from "../../components/zero/ImageUploadButton"


export class TestCros extends React.Component<{}, {}>{

    constructor(props: {}) {
        super(props)
    }

    send(url: string) {
        console.log("run:" + url)
        fetch(url, { credentials: 'include' })
            .then((feedback) => {
                console.log("feedback:", feedback)
                return feedback.json()
            }).then((json) => {
                console.log("json:", json)
            })

    }

    sendWithoutCookie(url: string) {
        console.log("run:" + url)
        fetch(url)
            .then((feedback) => {
                console.log("feedback:", feedback)
                return feedback.json()
            }).then((json) => {
                console.log("json:", json)
            })

    }

    render() {
        return (
            <div className="container">
                <h1>Test CROS</h1>
                <ButtonToolbar>
                    <Button onClick={this.send.bind(this, "http://192.168.1.82:8084/Zero/api/v1/test/demos")}>self</Button>
                    <Button onClick={this.send.bind(this, "http://localhost:8084/CheckShop/api/v1/test/demos")}>localhost</Button>
                    <Button onClick={this.sendWithoutCookie.bind(this, "http://localhost:8084/CheckShop/api/v1/test/demos")}>localhost without cookie</Button>
                    <Button onClick={this.send.bind(this, "https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyBFc_sx3NVIJ45ceAg5_kkBuiysKCUPj4I")}>google map</Button>
                    <Button onClick={this.send.bind(this, "https://graph.facebook.com/")}>facebook</Button>
                    <Button onClick={this.send.bind(this, "https://api.sandbox.paypal.com/v1/identity/openidconnect/userinfo/?schema=openid")}>paypal</Button>
                    <Button onClick={this.send.bind(this, "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY")}>nasa</Button>
                </ButtonToolbar>
            </div>
        )
    }
}
