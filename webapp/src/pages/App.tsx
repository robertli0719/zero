import * as React from "react"
import { connect } from "react-redux"
import { Router, Route, hashHistory, IndexRoute, RouterState, RedirectFunction } from 'react-router'
import { AppNavbar, NavbarItem } from "../components/zero/AppNavbar"
import * as me from "../actions/me"
import { UserProfile } from "../reducers/me"
import { store, AppState } from "../Store"

const navBarItemList = [
    { name: "Index", url: "/index" },
    { name: "About", url: "/about" },
    { name: "Test", url: "/test/index" }
]

type ReduxProps = {
    me: UserProfile
}

type CommonProps = {
    params: any
}

type Props = ReduxProps & CommonProps

class AppPage extends React.Component<Props, {}>{

    constructor() {
        super()
    }

    logout() {
        store.dispatch(me.triggerLogout()).then(() => {
            hashHistory.replace("/")
        })
    }

    render() {
        let rightNavBarItemList = [
            // { name: "Link", url: "/" },
            // { name: "Link", url: "/" }
        ] as NavbarItem[]

        if (me.isGeneralUser()) {
            rightNavBarItemList = [
                { name: this.props.me.authLabel, url: "/" },
                { name: "logout", onClick: this.logout.bind(this) }
            ]
        } else if (me.isAdmin()) {

        } else if (me.isLogged() == false) {
            rightNavBarItemList = [
                { name: "register", url: "auth/register" },
                { name: "login", url: "auth/login" }
            ]
        }

        return (
            <div>
                <AppNavbar name="React-Bootstrap" brandUrl="/" itemList={navBarItemList} rightItemList={rightNavBarItemList} />
                {this.props.children}
            </div>
        )
    }
}

function select(state: AppState): ReduxProps {
    return { me: state.me }
}

export const App = connect(select)(AppPage)