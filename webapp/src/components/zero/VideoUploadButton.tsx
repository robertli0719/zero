/*
 * Copyright 2017 Carter Li & Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-04-09
 */
import * as React from "react"
import { Sizes, Button, Modal, FormControl, HelpBlock, ControlLabel } from "react-bootstrap"
import { makeRandomString } from "../../utilities/random-coder"
import { http, RestErrorDto } from "../../utilities/http"

type VideoUploadButtonState = {
    showModal: boolean
    videoUrl: string
    files: FileList
}

type VideoUploadButtonProps = {
    active?: boolean
    block?: boolean
    bsStyle?: string
    bsSize?: Sizes
    onSuccess: (videoUrl: string) => void
}

export class VideoUploadButton extends React.Component<VideoUploadButtonProps, VideoUploadButtonState>{

    private controlId = makeRandomString(32)

    constructor() {
        super()
        this.state = {
            showModal: false,
            videoUrl: null,
            files: null
        }
    }

    open() {
        this.setState({ showModal: true })
    }

    close() {
        this.setState({ showModal: false })
    }

    use() {
        this.props.onSuccess(this.state.videoUrl)
        this.close()
    }

    createFormData(): FormData {
        const files: FileList = this.state.files
        const formData = new FormData()
        for (const i in files) {
            const fe: File = files[i]
            console.log("file size:", fe.size)
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
            }).then((videoUrl: string) => {
                this.setState({ videoUrl: videoUrl })
            })
    }

    onChange(event: React.FormEvent<HTMLInputElement>) {
        const files = event.currentTarget.files
        this.setState({ files: files })
        setTimeout(this.upload.bind(this), 0)
    }

    render() {
        const headerText = "Video Upload"
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
                        <FormControl type="file" onChange={this.onChange.bind(this)} />
                    </Modal.Body>
                    <Modal.Footer>
                        <Button bsStyle="success" disabled={!this.state.videoUrl} onClick={this.use.bind(this)}>Use</Button>
                        <Button onClick={this.close.bind(this)}>Close</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }
}