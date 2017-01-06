import * as React from "react"
import * as ReactDOM from "react-dom"
import { store } from "../../Store"
import { Button, ButtonToolbar, FormControl, FormGroup, Col } from "react-bootstrap"
// import { Cropper, CropResult } from "../../components/zero/ZCropper"

export class TestCropper extends React.Component<{}, {}>{

    constructor(props: {}) {
        super(props);
    }

    // onChange(result: CropResult) {
    //     console.log("x:y", result.x, result.y)
    //     console.log("width:height", result.width, result.height)
    // }

    // render() {
    //     return (
    //         <div className="container">
    //             <h1>Test Cropper</h1>
    //             <Cropper
    //                 onChange={this.onChange.bind(this)}
    //                 aspectRatio={10 / 5}
    //                 src="https://fengyuanchen.github.io/cropperjs/images/picture.jpg"
    //                 />
    //         </div>
    //     );
    // }
}
