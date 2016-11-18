import * as React from "react";
import * as ReactDOM from "react-dom";
import { Button, ButtonToolbar, Navbar, Nav, NavItem, NavbarProps } from "react-bootstrap";
import { LinkContainer } from 'react-router-bootstrap';
import './zero.scss';

let template = (
    <Navbar className="navbar" collapseOnSelect>
        <Navbar.Header>
            <Navbar.Brand>
                <a className="hello" href="#">React-Bootstrap</a>
            </Navbar.Brand>
            <Navbar.Toggle />
        </Navbar.Header>
        <Navbar.Collapse>
            <Nav>
                <LinkContainer to="/index">
                    <NavItem eventKey={1}>Index</NavItem>
                </LinkContainer>
                <LinkContainer to="/about">
                    <NavItem eventKey={2}>About</NavItem>
                </LinkContainer>
            </Nav>
            <Nav pullRight>
                <NavItem eventKey={1} href="#">Link Right</NavItem>
                <NavItem eventKey={2} href="#">Link Right</NavItem>
            </Nav>
        </Navbar.Collapse>
    </Navbar>
);

export class AppNavbar extends React.Component<{}, {}>{
    render() {
        return template;
    }
}
