import * as React from "react"
import { connect } from "react-redux"
import { hashHistory } from 'react-router'
import { store, AppState } from "../../Store"
import { AppNavbar, NavbarItem } from "../../components/zero/AppNavbar"
import { UserProfile } from "../../reducers/me"
import * as me from "../../actions/me"

function logout(redirect: string) {
    store.dispatch(me.triggerLogout()).then(() => {
        hashHistory.replace(redirect)
    })
}

function makeNavbarItemList(platform: string): NavbarItem[] {
    const perfix = "dashboard/" + platform + "/"
    return [
        { name: "Index", url: perfix + "index" },
        { name: "Inventory", url: perfix + "inventory" },
        { name: "Report", url: perfix + "report" },
    ]
}

function makeRightNavbarItemList(platform: string): NavbarItem[] {
    const redirect = "dashboard/" + platform + "/login"
    return [
        // { name: "Logout", onClick: logout(platform) }
        { name: "Logout", onClick: () => { logout(redirect) } }
    ]
}

type ReduxProp = {
    me: UserProfile
}

type CommonProp = {
    params: any
}

type Props = ReduxProp & CommonProp
type State = {
    platform: string
    navbarItemList: NavbarItem[]
    rightNavbarItemList: NavbarItem[]
    indexUrl: string
}

class StaffAppComponent extends React.Component<Props, State>{

    constructor(props: Props) {
        super(props)
        const platform = this.props.params['platform']
        const navbarItemList = makeNavbarItemList(platform)
        const rightNavbarItemList = makeRightNavbarItemList(platform)
        const indexUrl = "dashboard/" + platform + "/index"
        this.state = { platform: platform, navbarItemList: navbarItemList, rightNavbarItemList: rightNavbarItemList, indexUrl: indexUrl }
        store.dispatch(me.loadProfile())
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
    return { me: state.me }
}

export const StaffApp = connect(select)(StaffAppComponent)