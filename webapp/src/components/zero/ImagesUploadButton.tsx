/*
 * Copyright 2017 Carter Li & Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-04-11
 */
import * as React from "react"
import { Sizes, Button, Modal, FormControl, HelpBlock, ControlLabel, Image } from "react-bootstrap"
import { makeRandomString } from "../../utilities/random-coder"
import { IF } from "./stl/IF"
import { http, RestErrorDto } from "../../utilities/http"

type ImagesUploadButtonState = {
    showModal: boolean
    imgUrlArray: string[]
    isUploading: boolean
    files: FileList
}

type ImagesUploadButtonProps = {
    active?: boolean
    block?: boolean
    bsStyle?: string
    bsSize?: Sizes
    onSuccess: (imgUrlArray: string[]) => void
}

export class ImagesUploadButton extends React.Component<ImagesUploadButtonProps, ImagesUploadButtonState>{

    private controlId = makeRandomString(32)

    constructor() {
        super()
        this.state = {
            showModal: false,
            imgUrlArray: [],
            isUploading: false,
            files: null
        }
    }

    open() {
        this.setState({ showModal: true })
    }

    close() {
        this.setState({ showModal: false, imgUrlArray: [] })
    }

    use() {
        this.props.onSuccess(this.state.imgUrlArray)
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

        this.setState({ isUploading: true })
        return http.postParams("images", this.createFormData())
            .then((text: string) => {
                return JSON.parse(text)
            }).then((json) => {
                if (json.length != 1) {
                    throw "Exception when get upload result, url list size is not 1."
                }
                return json[0]
            }).then((imgUrl: string) => {
                const imgUrlArray = this.state.imgUrlArray
                imgUrlArray.push(imgUrl)
                this.setState({ imgUrlArray: imgUrlArray, isUploading: false })
            })
    }

    onChange(event: React.FormEvent<HTMLInputElement>) {
        const files = event.currentTarget.files
        this.setState({ files: files })
        setTimeout(this.upload.bind(this), 0)
    }

    render() {
        const headerText = "Images Upload"
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
                        {
                            this.state.imgUrlArray.map((imgUrl) => {
                                return <Image src={imgUrl} responsive />
                            })
                        }
                        <IF test={this.state.isUploading}>
                            <p>uploading...</p>
                        </IF>
                        <FormControl type="file" onChange={this.onChange.bind(this)} />
                    </Modal.Body>
                    <Modal.Footer>
                        <Button bsStyle="success" disabled={this.state.imgUrlArray.length == 0} onClick={this.use.bind(this)}>Use</Button>
                        <Button onClick={this.close.bind(this)}>Close</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }
}