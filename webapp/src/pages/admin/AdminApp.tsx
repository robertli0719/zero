import * as React from "react";
import { connect } from "react-redux"
import { hashHistory } from 'react-router'
import { store, AppState } from "../../Store"
import { AppNavbar, NavbarItem } from "../../components/zero/AppNavbar"
import { UserProfile } from "../../reducers/me"
import * as me from "../../actions/me"

function logout() {
    store.dispatch(me.triggerLogout()).then(() => {
        hashHistory.replace("admin/login");
    });
}

function testFun() {
    console.log("testFum run");
}

let userMenuList = [
    { name: "Platform", url: "admin/user-platform" },
    { name: "Role", url: "admin/user-role" },
    { name: "Promission" },
] as NavbarItem[];

let navBarItemList = [
    { name: "Index", url: "admin/index" },
    { name: "User", childs: userMenuList },
    { name: "Items1" },
    { name: "Items2" },
    { name: "Items3" }
] as NavbarItem[];

let rightNavBarItemList = [
    { name: "Logout", onClick: logout }
    // { name: "Link", url: "/" },
    // { name: "Link", url: "/" }
] as NavbarItem[];

interface Prop {
    me: UserProfile
}

class AdminAppComponent extends React.Component<Prop, {}>{

    constructor() {
        super();
        store.dispatch(me.loadProfile());
    }

    render() {
        let navbar = <AppNavbar name="Admin Dashboard" inverse={true} brandUrl="admin/index" itemList={navBarItemList} rightItemList={rightNavBarItemList} />
        if (this.props.me.userPlatformName == null) {
            navbar = <div></div>
        } else if (this.props.me.userPlatformName != "admin") {
            return (
                <div>
                    {navbar}
                    <p>A user who is not an admin is logged in now, please logout first.</p>
                </div>
            )
        }
        return (
            <div>
                {navbar}
                {this.props.children}
            </div>
        )
    }
}

function select(state: AppState): Prop {
    return { me: state.me };
}

export let AdminApp = connect(select)(AdminAppComponent);