package com.gevorg.mvvm.observer

import com.gevorg.mvvm.model.ErrorResult


class DataResult<T> {

    var data: T? = null
    var state: State? = null
    var error: ErrorResult? = null

    enum class State {
        IN_PROGRESS,
        SUCCESSFUL,
        ERROR
    }

    constructor(data: T?) {
        this.state = State.SUCCESSFUL
        this.data = data
    }

    constructor(state: State) {
        this.state = state
    }

    constructor(error: ErrorResult) {
        this.state = State.ERROR
        this.error = error
    }

    fun isSuccessful(): Boolean {
        return state == State.SUCCESSFUL
    }

    fun hasError(): Boolean {
        return state == State.ERROR
    }
}