package com.gevorg.mvvm.model

enum class ViewState {
    START_PROGRESS,
    STOP_PROGRESS,
    SHOW_ERROR;

    var value: String = ""

    override fun toString(): String {
        return value
    }

}