import * as React from "react"
import * as ReactDOM from "react-dom"
import { store } from "../../Store"
import { Button, ButtonToolbar, FormControl, FormGroup, Col } from "react-bootstrap"
import { ImageUploadButton } from "../../components/zero/ImageUploadButton"

// import { Cropper, CropResult } from "../../components/zero/ZCropper"

export class TestImageUpload extends React.Component<{}, {}>{

    constructor(props: {}) {
        super(props);
    }

    onUploadSuccess(url: string) {
        console.log("onUploadSuccess:", url);
    }

    render() {
        return (
            <div className="container">
                <h1>Test ImageUpload</h1>
                <ImageUploadButton option="cropped" onSuccess={this.onUploadSuccess.bind(this)} >
                    12345678
                </ImageUploadButton>
            </div>
        );
    }
}
