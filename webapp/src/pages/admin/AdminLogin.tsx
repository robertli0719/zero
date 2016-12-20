import * as React from "react";
import * as ReactDOM from "react-dom";
import { connect } from "react-redux"
import { Button, ButtonToolbar, ControlLabel, FormControl, Form, FormGroup, Checkbox, Col, Row } from "react-bootstrap"
import { authService, UserAuthDto } from "../../services/AuthService"
import { testService } from "../../services/TestService"
import { store, AppState } from "../../Store"

interface AdminLoginState {
    userAuthDto: UserAuthDto
    btnDisable: boolean
}

interface Prop {
    val: number
}

export class AdminLoginPage extends React.Component<Prop, AdminLoginState>{

    constructor() {
        super();
        this.state = {
            userAuthDto: { username: "", password: "", platform: "default", userType: "admin" },
            btnDisable: false
        }
    }

    appInit() {
        console.log(document.cookie);
    }

    fieldOnChange(event: React.FormEvent<HTMLInputElement>) {
        let name = event.currentTarget.name;
        let val = event.currentTarget.value;
        switch (name) {
            case "username":
                this.state.userAuthDto.username = val;
                break;
            case "password":
                this.state.userAuthDto.password = val;
                break;
        }
        this.setState(this.state);
    }

    runSomethingBig() {
        let sum = 0;
        for (let a = 0; a < 1024 * 1024; a++) {
            for (let b = 0; b < 1024; b++) {
                sum += a * b;
                sum %= 10000;
            }
        }
    }

    submit() {
        this.state.btnDisable = true;
        this.setState(this.state);
        console.log("before dispatch", store.getState());
        testService.addNumber(1);//计数器判断执行次数
        console.log("after dispatch", store.getState());
        //authService.login(this.state.userAuthDto);
        this.runSomethingBig();//运行一个很耗时的操作，故意拖慢速度
    }

    render() {
        console.log("adminLogin render");
        return (
            <div className="container">
                <h1>Admin Login</h1>
                <Row>
                    <Col xs={12} sm={6} md={4}>
                        <Form horizontal>
                            <FormGroup controlId="formHorizontalUsername">
                                <Col componentClass={ControlLabel} sm={3}>Username</Col>
                                <Col sm={9}>
                                    <FormControl name="username" type="text" placeholder="Username" onChange={this.fieldOnChange.bind(this)} />
                                </Col>
                            </FormGroup>

                            <FormGroup controlId="formHorizontalPassword">
                                <Col componentClass={ControlLabel} sm={3}>Password</Col>
                                <Col sm={9}>
                                    <FormControl name="password" type="password" placeholder="Password" onChange={this.fieldOnChange.bind(this)} />
                                </Col>
                            </FormGroup>

                            <FormGroup>
                                <Col smOffset={3} sm={9}>
                                    <a className="btn btn-default" disabled={this.state.btnDisable} onClick={this.submit.bind(this)}>Login</a>
                                </Col>
                            </FormGroup>
                        </Form>
                    </Col>
                </Row>
                <ButtonToolbar>
                    <Button bsStyle="success" disabled={this.state.btnDisable} onClick={this.appInit.bind(this)}>OK</Button>
                </ButtonToolbar>
            </div>
        );
    }
}

function select(state: AppState): Prop {
    console.log("select in AdminLogin...")
    return { val: state.test.val };
}

export let AdminLogin = connect(select)(AdminLoginPage);