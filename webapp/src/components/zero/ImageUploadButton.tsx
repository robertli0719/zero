/*
 * Copyright 2017 Carter Li & Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.4 2017-02-28
 */
import * as React from "react"
import * as ReactDOM from "react-dom"
import { Sizes, Button, ButtonToolbar, Image, Navbar, Nav, NavItem, NavbarProps, Popover, Tooltip, Modal, OverlayTrigger } from "react-bootstrap"
import { Cropper, CropResult } from "../../components/zero/ZCropper"
import { http, RestErrorDto } from "../../utilities/http"
import "../../../node_modules/cropperjs/dist/cropper.css"

export type UploadOption = 'default' | 'cropped' | 'fixed'

const MODAL_HEIHT: string = "500px"

interface ModalState {
    showModal: boolean
    imgURL: string
    files: FileList
    option: UploadOption
    reset: () => void
}

interface Props {
    option?: UploadOption
    fixedWidth?: number
    fixedHeight?: number
    active?: boolean
    block?: boolean
    bsStyle?: string
    bsSize?: Sizes
    onSuccess: (imgUrl: string) => void
}

export class ImageUploadButton extends React.Component<Props, ModalState>{

    private cropResult: CropResult

    constructor(props: Props) {
        super(props)
        const option: UploadOption = props.option ? props.option : "default"
        if (props.option != "fixed" && (props.fixedHeight || props.fixedWidth)) {
            throw "fixedHeight and fixedWidth can only use with option=fixed"
        }
        if (props.option == "fixed" && (!props.fixedHeight || !props.fixedWidth)) {
            throw "need fixedHeight and fixedWidth when using option=fixed"
        }
        this.state = { showModal: false, files: null, imgURL: null, reset: null, option: option }
    }

    reset() {
        this.state.reset()
    }

    close() {
        this.setState({ showModal: false, imgURL: null, files: null })
    }

    open() {
        this.setState({ showModal: true })
    }

    use() {
        this.props.onSuccess(this.state.imgURL)
        this.close()
    }

    createFormData(): FormData {
        const files: FileList = this.state.files
        const formData = new FormData()
        for (const i in files) {
            const fe: File = files[i]
            formData.append("file", fe)
        }
        return formData
    }

    upload() {
        if (!this.state.files) {
            throw "Exception when method upload(): have not choose file"
        }

        return http.postParams("images", this.createFormData())
            .then((text: string) => {
                return JSON.parse(text)
            }).then((json) => {
                if (json.length != 1) {
                    throw "Exception when get upload result, url list size is not 1."
                }
                return json[0]
            }).then((imgUrl: string) => {
                this.setState({ imgURL: imgUrl })
            })
    }

    createCropForm(data: CropResult): FormData {
        const formData = new FormData()
        formData.append("x", Math.round(data.x))
        formData.append("y", Math.round(data.y))
        formData.append("width", Math.round(data.width))
        formData.append("height", Math.round(data.height))
        return formData
    }

    createFixForm(data: CropResult): FormData {
        const formData = new FormData()
        formData.append("x", Math.round(data.x))
        formData.append("y", Math.round(data.y))
        formData.append("width", Math.round(data.width))
        formData.append("height", Math.round(data.height))
        formData.append("fixedHeight", this.props.fixedHeight)
        formData.append("fixedWidth", this.props.fixedWidth)
        return formData
    }

    crop() {
        const id = this.state.imgURL.slice(-36)
        const url = "images/cropper/" + id
        http.postParams(url, this.createCropForm(this.cropResult))
            .then((response) => {
                const imgURL = response
                this.props.onSuccess(imgURL)
                this.close()
            })
    }

    fix() {
        const id = this.state.imgURL.slice(-36)
        const url = "images/fixer/" + id
        http.postParams(url, this.createFixForm(this.cropResult))
            .then((response) => {
                const imgURL = response
                this.props.onSuccess(imgURL)
                this.close()
            })
    }

    fileInputOnChange(event: React.FormEvent<HTMLInputElement>) {
        const files = event.currentTarget.files
        this.setState({ files: files })
        setTimeout(this.upload.bind(this), 0)
    }

    onChange(result: CropResult) {
        this.cropResult = result
    }

    resetFunBack(reset: () => void) {
        this.setState({ reset: reset })
    }

    render() {
        let btnBar = <div></div>
        switch (this.state.option) {
            case "default":
                btnBar = <div>
                    <Button bsStyle="success" disabled={!this.state.imgURL} onClick={this.use.bind(this)}>Use</Button>
                    <Button onClick={this.close.bind(this)}>Close</Button>
                </div>
                break
            case "cropped":
                btnBar = <div>
                    <p>uml:{this.state.imgURL}</p>
                    <Button bsStyle="success" disabled={!this.state.imgURL} onClick={this.crop.bind(this)}>Crop</Button>
                    <Button onClick={this.reset.bind(this)}>Reset</Button>
                    <Button onClick={this.close.bind(this)}>Close</Button>
                </div>
                break
            case "fixed":
                btnBar = <div>
                    <Button bsStyle="success" disabled={!this.state.imgURL} onClick={this.fix.bind(this)}>Fix</Button>
                    <Button onClick={this.reset.bind(this)}>Reset</Button>
                    <Button onClick={this.close.bind(this)}>Close</Button>
                </div>
                break
        }

        let modalBody = <div></div>
        if (this.state.imgURL == null) {
            modalBody = <div className="container">
                <input name="file" type="file" onChange={this.fileInputOnChange.bind(this)} multiple={false} />
            </div>
        } else {
            switch (this.state.option) {
                case "default":
                    modalBody = <Image src={this.state.imgURL} responsive />
                    break
                case "cropped":
                    modalBody = <Cropper
                        resetFunBack={this.resetFunBack.bind(this)}
                        onChange={this.onChange.bind(this)}
                        src={this.state.imgURL}
                        style={{ width: "100%", height: MODAL_HEIHT }}
                    />
                    break
                case "fixed":
                    modalBody = <Cropper
                        resetFunBack={this.resetFunBack.bind(this)}
                        onChange={this.onChange.bind(this)}
                        aspectRatio={this.props.fixedWidth / this.props.fixedHeight}
                        src={this.state.imgURL}
                        style={{ width: "100%", height: MODAL_HEIHT }}
                    />
                    break
            }
        }

        let headerText = "Image Upload"
        if (this.state.option == "fixed") {
            headerText = "Image Upload for " + this.props.fixedWidth + "X" + this.props.fixedHeight
        }

        return (
            <div>
                <Button
                    bsStyle={this.props.bsStyle}
                    bsSize={this.props.bsSize}
                    block={this.props.block}
                    active={this.props.active}
                    onClick={this.open.bind(this)}
                >
                    {this.props.children}
                </Button>

                <Modal show={this.state.showModal} onHide={this.close.bind(this)}>
                    <Modal.Header closeButton>
                        <Modal.Title>{headerText}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {modalBody}
                    </Modal.Body>
                    <Modal.Footer>
                        {btnBar}
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }
}
