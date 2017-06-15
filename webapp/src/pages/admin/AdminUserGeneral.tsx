import * as React from "react"
import { connect } from "react-redux"
import { store, AppState } from "../../Store"
import { Button, ButtonToolbar, Row, Col, Panel } from "react-bootstrap"
import * as zform from "../../components/zero/zform/zform"
import * as zview from "../../components/zero/zview/zview"

interface Prop {
}

interface State {
    uri: string
    header: string
}

class AdminUserGeneralComponent extends React.Component<Prop, State>{

    constructor(prop: Prop) {
        super(prop)
        this.state = { uri: "users", header: "All Users" }
    }

    search(dto: any) {
        const keyword = encodeURIComponent(dto.keyword)
        this.setState({
            uri: "users/searcher?words=" + keyword,
            header: "Search for " + dto.keyword
        })
    }

    showAll() {
        this.setState({ uri: "users", header: "All Users" })
    }

    render() {
        return (
            <div className="container">
                <h1>User Management</h1>
                <Row>
                    <Col sm={3}>
                        <Panel header="Search Panel" bsStyle="primary">
                            <zform.Form onSubmit={this.search.bind(this)} >
                                <zform.TextField name="keyword" label="keyword" enterSubmit={true} />
                                <zform.Submit block value="Search" />
                            </zform.Form>
                            <hr />
                            <Button onClick={this.showAll.bind(this)} block>Show All</Button>
                        </Panel>
                    </Col>
                    <Col sm={9}>
                        <zview.View header={this.state.header} bsStyle="primary" uri={this.state.uri}></zview.View>
                    </Col>
                </Row>
            </div>
        )
    }
}

function select(state: AppState): Prop {
    return {}
}

export const AdminUserGeneral = connect(select)(AdminUserGeneralComponent)