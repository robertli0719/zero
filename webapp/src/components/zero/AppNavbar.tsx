/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
import * as React from "react";
import * as ReactDOM from "react-dom";
import { Button, ButtonToolbar, Navbar, Nav, NavItem, NavbarProps } from "react-bootstrap";
import { LinkContainer } from 'react-router-bootstrap';
import './zero.scss';

export interface NavbarItem {
    name: string
    url: string
}

interface AppNavbarProp {
    name: string
    itemList: NavbarItem[]
    rightItemList: NavbarItem[]
}

export class AppNavbar extends React.Component<AppNavbarProp, {}>{
    render() {
        return (
            <Navbar className="navbar" collapseOnSelect>
                <Navbar.Header>
                    <Navbar.Brand>
                        <a href="#">React-Bootstrap</a>
                    </Navbar.Brand>
                    <Navbar.Toggle />
                </Navbar.Header>
                <Navbar.Collapse>
                    <Nav>
                        {this.props.itemList.map((item, index) => {
                            return (
                                <LinkContainer to={item.url}>
                                    <NavItem eventKey={index}>{item.name}</NavItem>
                                </LinkContainer>
                            )
                        })}
                    </Nav>
                    <Nav pullRight>
                        {this.props.rightItemList.map((item, index) => {
                            return (
                                <LinkContainer to={item.url}>
                                    <NavItem eventKey={index}>{item.name}</NavItem>
                                </LinkContainer>
                            )
                        })}
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}
