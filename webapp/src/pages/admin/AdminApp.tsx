import * as React from "react"
import { connect } from "react-redux"
import { hashHistory } from 'react-router'
import { store, AppState } from "../../Store"
import { AppNavbar, NavbarItem } from "../../components/zero/AppNavbar"
import { UserProfile } from "../../reducers/me"
import * as me from "../../actions/me"

function logout() {
    store.dispatch(me.triggerLogout()).then(() => {
        hashHistory.replace("admin/login")
    })
}

const userMenuList = [
    { name: "General User List", url: "admin/user-general" },
    { name: "Admin Editor", url: "admin/user-admin" },
    { name: "Platform Editor", url: "admin/user-platform" },
    { name: "Role", url: "admin/user-role" },
    { name: "Promission" },
] as NavbarItem[]

const navBarItemList = [
    { name: "Index", url: "admin/index" },
    { name: "User", childs: userMenuList },
    { name: "Items1" },
    { name: "Items2" },
    { name: "Items3" }
] as NavbarItem[]

interface Prop {
    me: UserProfile
}

interface State {
}

class AdminAppComponent extends React.Component<Prop, State>{

    constructor() {
        super()
        store.dispatch(me.loadProfile())
    }

    makeRightNavbarItems(): NavbarItem[] {
        if (this.props.me.authLabel) {
            return [
                { name: this.props.me.authLabel, url: "/admin/index" },
                { name: "Logout", onClick: logout }
            ]
        }
        return [{ name: "Logout", onClick: logout }]
    }

    render() {
        const navbar = <AppNavbar name="Admin Dashboard" inverse={true} brandUrl="admin/index" itemList={navBarItemList} rightItemList={this.makeRightNavbarItems()} />
        return (
            <div>
                {navbar}
                {this.props.children}
            </div>
        )
    }
}

function select(state: AppState): Prop {
    return { me: state.me }
}

export const AdminApp = connect(select)(AdminAppComponent)