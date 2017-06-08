import * as React from "react"
import { connect } from "react-redux"
import { hashHistory, Link } from "react-router"
import { Button, ButtonToolbar, ControlLabel, FormControl, Form, FormGroup, Checkbox, Col, Row, Panel } from "react-bootstrap"
import * as me from "../../actions/me"
import { store, AppState } from "../../Store"
import { UserProfile } from "../../reducers/me"
import * as zform from "../zero/zform/zform"
import { Selection } from "../zero/zform/zform_schema"
import * as utilities from "../../utilities/random-coder"
import { RestErrorDto } from "../../utilities/http"
import { IF } from "../zero/stl/IF"

const countryCodeSelections: Selection[] = [
    { label: "+1 America", value: "1" },
    { label: "+1 Canada", value: "1" },
    { label: "+7 Russia", value: "7" },
    { label: "+33 France", value: "33" },
    { label: "+44 United Kingdom", value: "44" },
    { label: "+49 Germany", value: "49" },
    { label: "+52 Mexico", value: "52" },
    { label: "+61 Australia", value: "61" },
    { label: "+81 Japan", value: "81" },
    { label: "+82 South Korea", value: "82" },
    { label: "+86 China", value: "86" },
    { label: "+852 Hong Kong", value: "852" },
    { label: "+886 Taiwan", value: "886" },
]

interface Props {
    me: UserProfile
}

interface State {
    sentOut: boolean
    countryCode: string
    phoneNumber: string
}

export class MobileBindingPanel extends React.Component<Props, State>{

    constructor(props: Props) {
        super(props)
        this.state = { sentOut: false, countryCode: null, phoneNumber: null }
    }

    onSubmitApplication(dto: any) {
        this.setState({ countryCode: dto.countryCode, phoneNumber: dto.phoneNumber })
    }

    afterSubmitApplication() {
        this.setState({ sentOut: true })
    }

    afterSubmitVerifiedCode() {
        store.dispatch(me.loadProfile()).then(() => {
            this.setState({ sentOut: false, countryCode: null, phoneNumber: null })
        })
    }

    render() {
        const me = this.props.me
        if (me.uid == null) {
            return null
        } else if (me.telephone != null) {
            return <p>Mobile Phone: {me.telephone}</p>
        } else if (this.state.sentOut) {
            return (
                <Panel header="Add Phone Number">
                    <h3>ENTER VERIFICATION CODE</h3>
                    <p>Please enter the verfication code we sent to your phone.</p>
                    <Col xs={12} sm={4} md={3}>
                        <zform.Form action="me/mobile-bindings" onSuccess={this.afterSubmitVerifiedCode.bind(this)}>
                            <zform.Hidden name="countryCode" value={this.state.countryCode} />
                            <zform.Hidden name="phoneNumber" value={this.state.phoneNumber} />
                            <zform.TextField name="verifiedCode" label="Verfication Code" enterSubmit={true} />
                            <zform.Submit value="Confirm" />
                        </zform.Form>
                    </Col>
                </Panel>
            )
        } else {
            return (
                <Panel header="Add Phone Number">
                    <h3>VERIFY YOUR PHONE NUMBER</h3>
                    <p>Increase account security by verifying a phone number</p>
                    <Col xs={12} sm={4} md={3}>
                        <zform.Form action="me/mobile-binding-applications"
                            onSuccess={this.afterSubmitApplication.bind(this)}
                            onSubmit={this.onSubmitApplication.bind(this)}
                        >
                            <zform.Select name="countryCode" label="Country Code" selections={countryCodeSelections} value="1" />
                            <zform.TextField name="phoneNumber" label="Phone Number" />
                            <zform.Submit value="Next" />
                        </zform.Form>
                    </Col>
                </Panel>
            )
        }
    }
}