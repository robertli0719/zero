/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.1.3 2017-04-11
 */
import * as React from "react"
import { TagSchema, FieldPlaceOption, Selection } from "./zform_schema"

type MapProps = {
    name: string
    label?: string
}

export class Map extends React.Component<MapProps, {}> { }

type TextFieldProps = {
    name: string
    label: string
    value?: string
    enterSubmit?: boolean
    place?: FieldPlaceOption
}

export class TextField extends React.Component<TextFieldProps, {}> {
}

export class Password extends React.Component<TextFieldProps, {}> { }

type TextareaProps = {
    name: string
    label: string
    value?: string
    // notNull?: boolean
}

export class Textarea extends React.Component<TextareaProps, {}> { }

type FileProps = {
    name: string
    label: string
    // notNull?: boolean
}

export class File extends React.Component<FileProps, {}> { }

type CheckBoxProps = {
    name: string
    label: string
}

export class CheckBox extends React.Component<CheckBoxProps, {}> { }

type CheckBoxesProps = {
    name: string
    label: string
    value?: string[]
    selections: Selection[]
}

export class CheckBoxes extends React.Component<CheckBoxesProps, {}> { }

type HiddenProps = {
    name: string
    value: string
    place?: FieldPlaceOption
}

export class Hidden extends React.Component<HiddenProps, {}> { }

type ConstantProps = {
    name: string
    label: string
    value: string
    place?: FieldPlaceOption
}

export class Constant extends React.Component<ConstantProps, {}> { }

type RadiosProps = {
    name: string
    label?: string
    value: string
    selections?: Selection[]
}

export class Radios extends React.Component<RadiosProps, {}> { }

export class Select extends React.Component<RadiosProps, {}> { }


type ImageUploadOption = 'default' | 'cropped' | 'fixed'

type ImageProps = {
    label: string
    name: string
    value?: string
    option?: ImageUploadOption
}

export class Image extends React.Component<ImageProps, {}> { }

type ImagesProps = {
    label: string
    name: string
    value?: string
}

export class Images extends React.Component<ImagesProps, {}> { }

type ImageShowerProps = {
    name: string
}

export class ImageShower extends React.Component<ImageShowerProps, {}> { }

type VideoProps = {
    label: string
    name: string
    value?: string
}

export class Video extends React.Component<VideoProps, {}> { }

type VideoPlayerProps = {
    name: string
}

export class VideoPlayer extends React.Component<VideoPlayerProps, {}> { }

type SubmitProps = {
    value?: string
    block?: boolean
    bsStyle?: string
}

export class Submit extends React.Component<SubmitProps, {}> { }
