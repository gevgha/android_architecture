package com.gevorg.mvvm.model

import com.gevorg.mvvm.api.ErrorCode


data class ErrorResult(
        var code: Int = ErrorCode.UNDEFINED,
        var detail: String = "",
        var throwable: Throwable? = null,
)
