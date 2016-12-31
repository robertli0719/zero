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

let navBarItemList = [
    { name: "Index", url: "admin/index" },
    { name: "Users", url: "admin/user" },
];

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
        return (
            <div>
                <AppNavbar name="Admin Dashboard" brandUrl="admin/index" itemList={navBarItemList} rightItemList={rightNavBarItemList} />
                {this.props.children}
            </div>
        )
    }
}

function select(state: AppState): Prop {
    return { me: state.me };
}

export let AdminApp = connect(select)(AdminAppComponent);