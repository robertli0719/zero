/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
import * as React from "react";
import * as ReactDOM from "react-dom";
import { Button, ButtonToolbar, FormControl, FormGroup, ControlLabel } from "react-bootstrap";
import { LinkContainer } from 'react-router-bootstrap';
import { RestErrorDto, RestErrorItemDto } from "../utilities/http"
import { FormState } from "../reducers/forms"

type FormErrorPanelProps = {
    formState: FormState
}

type FormErrorPanelState = {

}

export class FormErrorPanel extends React.Component<FormErrorPanelProps, FormErrorPanelState>{

    constructor(props: FormErrorPanelProps) {
        super(props);

    }

    render() {
        if (!(this.props.formState && this.props.formState.restError)) {
            return <div></div>
        }

        let errors: RestErrorItemDto[] = this.props.formState.restError.errors;
        return (
            <div>
                {errors.map((error) => {
                    return <p>{error.source} : {error.message}</p>
                })}
            </div>
        );
    }
}