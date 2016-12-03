import * as React from "react";
import * as ReactDOM from "react-dom";
import { Button, ButtonToolbar, FormControl, FormGroup, ControlLabel, Col, Row } from "react-bootstrap"
import { Link } from "react-router"
import { connect } from "react-redux"
import { AppState } from "../../Store"

interface UserRegisterVerifiyProps {
    params: any
}

interface UserRegisterVerifiyState {
    result: any;
}

export class UserRegisterVerifiy extends React.Component<UserRegisterVerifiyProps, UserRegisterVerifiyState>{

    constructor(props: UserRegisterVerifiyProps) {
        super(props);
        this.state = { result: "NONE" }
        this.submit();
    }

    formSubmitSuccessHandler(feedback: any) {
        let result = feedback["result"];
        this.state.result = result;
        this.setState(this.state);
        console.log("success:", feedback["result"]);
    }

    formSubmitErrorHandler() {

    }

    submit() {
        let code = this.props.params['code'];
        $.ajax({
            url: "auth/register/verifiy",
            method: "post",
            data: {
                verifiedCode: code
            },
            success: this.formSubmitSuccessHandler.bind(this),
            error: this.formSubmitErrorHandler.bind(this)
        });
    }

    render() {
        let message = "No verifiy result";
        switch (this.state.result) {
            case "VERIFIY_SUCCESS":
                message = "验证成功 verifiy successfully";
                break;
            case "VERIFIED_CODE":
                message = "已经验证成功 had verified";
                break;
            case "VERIFIY_FAIL":
                message = "验证失败 verifiy failed";
                break;
        }
        return (
            <div className="container">
                <h1>User Register Verifiy</h1>
                <p>code: {this.props.params.code}</p>
                <p>{message}</p>
                <Link className="btn btn-success" to="auth/login" >Login</Link>
                <Row>
                    <Col sm={3}>
                    </Col>
                </Row>
            </div>
        );
    }
}