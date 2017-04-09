/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0 2017-01-01
 */
import * as React from "react"
import * as ReactDOM from "react-dom"
import { Button, ButtonToolbar, Navbar, Nav, NavItem, NavbarProps, NavItemProps, NavDropdown, MenuItem } from "react-bootstrap"
import { Link } from 'react-router'
import { LinkContainer } from 'react-router-bootstrap'

export interface NavbarItem {
    name: string
    url?: string
    onClick?: React.EventHandler<React.MouseEvent<React.ClassicComponent<NavItemProps, {}>>>
    childs?: NavbarItem[]
}

interface AppNavbarProp {
    name: string
    brandUrl: string
    itemList: NavbarItem[]
    rightItemList: NavbarItem[]
    inverse?: boolean
}

export class AppNavbar extends React.Component<AppNavbarProp, {}>{

    createMenuItem(item: NavbarItem, index: number): JSX.Element {
        if (item.url) {
            return (
                <LinkContainer to={item.url}>
                    <MenuItem eventKey={index}>{item.name}</MenuItem>
                </LinkContainer>
            )
        } else if (item.onClick) {
            return <MenuItem onClick={item.onClick} eventKey={index}>{item.name}</MenuItem>
        } else if (item.childs && item.childs.length > 0) {
            throw "AppNavbar can't support submenu so far. -> " + item.name
        } else {
            return <MenuItem disabled eventKey={index}>{item.name}</MenuItem>
        }
    }

    createMenu(item: NavbarItem, index: number) {
        const navbarId = "nav-dropdown-" + index
        return (
            <NavDropdown eventKey={index + 1} title={item.name} id={navbarId}>
                {
                    item.childs.map((item, subindex) => {
                        const i = index + 1 + (subindex + 1) / 10
                        return this.createMenuItem(item, i)
                    })
                }
            </NavDropdown>
        )
    }

    createNavbarItem(item: NavbarItem, index: number) {
        if (item.url) {
            return (
                <LinkContainer to={item.url}>
                    <NavItem eventKey={index + 1}>{item.name}</NavItem>
                </LinkContainer>
            )
        } else if (item.onClick) {
            return <NavItem onClick={item.onClick} eventKey={index + 1}>{item.name}</NavItem>
        } else if (item.childs && item.childs.length > 0) {
            return this.createMenu(item, index)
        } else {
            return <NavItem disabled eventKey={index + 1}>{item.name}</NavItem>
        }
    }

    render() {
        return (
            <Navbar className="navbar" inverse={this.props.inverse} collapseOnSelect>
                <Navbar.Header>
                    <Navbar.Brand>
                        <Link to={this.props.brandUrl}>{this.props.name}</Link>
                    </Navbar.Brand>
                    <Navbar.Toggle />
                </Navbar.Header>
                <Navbar.Collapse>
                    <Nav>
                        {this.props.itemList.map((item, index) => {
                            return this.createNavbarItem(item, index)
                        })}
                    </Nav>
                    <Nav pullRight>
                        {this.props.rightItemList.map((item, index) => {
                            return this.createNavbarItem(item, index)
                        })}
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        )
    }
}
