/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
import * as React from "react";
import * as ReactDOM from "react-dom";
import { Button, ButtonToolbar, Navbar, Nav, NavItem, NavbarProps, NavItemProps } from "react-bootstrap";
import { Link } from 'react-router';
import { LinkContainer } from 'react-router-bootstrap';
import './zero.scss';

export interface NavbarItem {
    name: string
    url?: string
    onClick?: React.EventHandler<React.MouseEvent<React.ClassicComponent<NavItemProps, {}>>>
}

interface AppNavbarProp {
    name: string
    brandUrl: string
    itemList: NavbarItem[]
    rightItemList: NavbarItem[]
}

export class AppNavbar extends React.Component<AppNavbarProp, {}>{

    createNavbarItems(item: NavbarItem, index: number) {
        if (item.url) {
            return (
                <LinkContainer to={item.url}>
                    <NavItem eventKey={index}>{item.name}</NavItem>
                </LinkContainer>
            )
        } else {
            return (
                <NavItem onClick={item.onClick} eventKey={index}>{item.name}</NavItem>
            )
        }
    }

    render() {
        return (
            <Navbar className="navbar" collapseOnSelect>
                <Navbar.Header>
                    <Navbar.Brand>
                        <Link to={this.props.brandUrl}>{this.props.name}</Link>
                    </Navbar.Brand>
                    <Navbar.Toggle />
                </Navbar.Header>
                <Navbar.Collapse>
                    <Nav>
                        {this.props.itemList.map((item, index) => {
                            return this.createNavbarItems(item, index);
                        })}
                    </Nav>
                    <Nav pullRight>
                        {this.props.rightItemList.map((item, index) => {
                            return this.createNavbarItems(item, index);
                        })}
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}
