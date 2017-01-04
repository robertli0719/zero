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

class TestAppComponent extends React.Component<Prop, {}>{

    constructor() {
        super();
        store.dispatch(me.loadProfile());
    }

    render() {
        let navbar = <AppNavbar name="Test" brandUrl="test/index" itemList={navBarItemList} rightItemList={rightNavBarItemList} />
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

export let TestApp = connect(select)(TestAppComponent);