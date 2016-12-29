import * as React from "react";
import { connect } from "react-redux"
import { store, AppState } from "../../Store"
import { AppNavbar, NavbarItem } from "../../components/zero/AppNavbar"
import { Auth } from "../../reducers/auth"
import * as auth from "../../actions/auth"

function logout() {
    store.dispatch(auth.triggerLogout());
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
    auth: Auth
}

class AdminAppComponent extends React.Component<Prop, {}>{

    constructor() {
        super();
        store.dispatch(auth.loadProfile());
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
    return { auth: state.auth };
}

export let AdminApp = connect(select)(AdminAppComponent);