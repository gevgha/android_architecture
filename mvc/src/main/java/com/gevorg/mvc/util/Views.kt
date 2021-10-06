package com.gevorg.mvc

import android.widget.EditText

fun EditText.toText():String{
    return this.text.toString()
}

fun EditText.toLength():Int{
    return this.text.toString().length
}