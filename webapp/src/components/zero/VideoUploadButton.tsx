/*
 * Copyright 2017 Carter Li & Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.2 2017-04-12
 */
import * as React from "react"
import { Sizes, Button, Modal, FormControl, HelpBlock, ControlLabel } from "react-bootstrap"
import { makeRandomString } from "../../utilities/random-coder"
import { IF } from "./stl/IF"
import { http, RestErrorDto } from "../../utilities/http"

type VideoUploadButtonState = {
    showModal: boolean
    videoUrl: string
    isUploading: boolean
    files: FileList
}

type VideoUploadButtonProps = {
    active?: boolean
    block?: boolean
    bsStyle?: string
    bsSize?: Sizes
    videoUrl: string
    onSuccess: (videoUrl: string) => void
}

export class VideoUploadButton extends React.Component<VideoUploadButtonProps, VideoUploadButtonState>{

    private controlId = makeRandomString(32)

    constructor(props: VideoUploadButtonProps) {
        super(props)
        this.state = {
            showModal: false,
            videoUrl: props.videoUrl,
            isUploading: false,
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

    remove() {
        this.setState({ videoUrl: null })
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

        this.setState({ isUploading: true })
        return http.postParams("videos", this.createFormData())
            .then((text: string) => {
                return JSON.parse(text)
            }).then((json) => {
                if (json.length != 1) {
                    throw "Exception when get upload result, url list size is not 1."
                }
                return json[0]
            }).then((videoUrl: string) => {
                this.setState({ videoUrl: videoUrl, isUploading: false })
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
                        <IF test={this.state.isUploading}>
                            <p>uploading...</p>
                        </IF>
                        <IF test={this.state.videoUrl != null && this.state.isUploading == false}>
                            <p>videoUrl: {this.state.videoUrl}</p>
                            <video width="100%" controls>
                                <source src={this.state.videoUrl} type="video/mp4" />
                                Your device does not support the video tag.
                            </video>
                        </IF>
                        <FormControl type="file" onChange={this.onChange.bind(this)} />
                    </Modal.Body>
                    <Modal.Footer>
                        <Button bsStyle="danger" disabled={!this.state.videoUrl} onClick={this.remove.bind(this)}>Remove</Button>
                        <Button bsStyle="success" disabled={this.state.isUploading} onClick={this.use.bind(this)}>Use</Button>
                        <Button onClick={this.close.bind(this)}>Close</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }
}