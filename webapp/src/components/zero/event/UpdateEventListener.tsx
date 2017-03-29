/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 * 
 * version 1.0.0 2017-03-28
 */

export class UpdateEventListener {

    private actionList: Function[]

    public constructor() {
        this.actionList = []
    }

    public addAction(action: Function) {
        this.actionList.push(action)
    }

    public trigger() {
        for (const fun of this.actionList) {
            fun()
        }
    }
}