import * as React from "react";
import { connect } from "react-redux"
import { hashHistory } from 'react-router'
import { store, AppState } from "../../Store"
import { AppNavbar, NavbarItem } from "../../components/zero/AppNavbar"
import { Auth } from "../../reducers/auth"
import * as auth from "../../actions/auth"

function logout(redirect: string) {
    store.dispatch(auth.triggerLogout()).then(() => {
        hashHistory.replace(redirect);
    });
}

function makeNavbarItemList(platform: string): NavbarItem[] {
    let perfix = "dashboard/" + platform + "/";
    return [
        { name: "Index", url: perfix + "index" },
        { name: "Inventory", url: perfix + "inventory" },
        { name: "Report", url: perfix + "report" },
    ];
}

function makeRightNavbarItemList(platform: string): NavbarItem[] {
    let redirect = "dashboard/" + platform + "/login";
    return [
        // { name: "Logout", onClick: logout(platform) }
        { name: "Logout", onClick: () => { logout(redirect) } }
    ];
}

type ReduxProp = {
    auth: Auth
}

type CommonProp = {
    params: any
}

type Props = ReduxProp & CommonProp;
type State = {
    platform: string
    navbarItemList: NavbarItem[]
    rightNavbarItemList: NavbarItem[]
    indexUrl: string
}

class StaffAppComponent extends React.Component<Props, State>{

    constructor(props: Props) {
        super(props);
        let platform = this.props.params['platform'];
        let navbarItemList = makeNavbarItemList(platform);
        let rightNavbarItemList = makeRightNavbarItemList(platform);
        let indexUrl = "dashboard/" + platform + "/index";
        this.state = { platform: platform, navbarItemList: navbarItemList, rightNavbarItemList: rightNavbarItemList, indexUrl: indexUrl }
        store.dispatch(auth.loadProfile());
    }

    render() {
        return (
            <div>
                <AppNavbar name="Store Dashboard"
                    brandUrl={this.state.indexUrl}
                    itemList={this.state.navbarItemList}
                    rightItemList={this.state.rightNavbarItemList} />
                {this.props.children}
            </div>
        )
    }
}

function select(state: AppState): ReduxProp {
    return { auth: state.auth };
}

export let StaffApp = connect(select)(StaffAppComponent);