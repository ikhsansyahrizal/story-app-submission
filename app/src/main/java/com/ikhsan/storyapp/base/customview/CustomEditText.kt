package com.ikhsan.storyapp.base.customview

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout

class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : AppCompatEditText(context, attrs) {

    private var minLength = 0
    private var maxLength = Int.MAX_VALUE
    private var errorMessage = "encounter unkown error"
    private var errorMessageEmail = "input should be email format"
    private var textInputLayout: TextInputLayout? = null
    private var isEmail = false

    init {
        setupTextChangeListener()
    }

    fun setEmailFormat(setEmail: Boolean) {
        isEmail = setEmail
    }

    fun setTextInputLayout(layout: TextInputLayout) {
        textInputLayout = layout
    }

    fun setMinLength(length: Int) {
        minLength = length
        validateInput()
    }

    fun setMaxLength(length: Int) {
        maxLength = length
        filters = arrayOf(InputFilter.LengthFilter(maxLength))
        validateInput()
    }

    fun setErrorMessage(message: String) {
        errorMessage = message
    }

    private fun setupTextChangeListener() {
        doAfterTextChanged {
            validateInput()
            validateInputEmail()
        }
    }

    private fun validateInput() {
        setErrorMessage("Minimum input should be ${minLength} characters")
        val input = text.toString()
        if (input.isEmpty()) {
            textInputLayout?.error = null
            return
        }
        if (input.length < minLength) {
            textInputLayout?.error = errorMessage
        } else {
            textInputLayout?.error = null
        }
    }

    private fun validateInputEmail() {
        if (isEmail) {
            val input = text.toString()
            if (input.isEmpty() || input.contains("@")) {
                textInputLayout?.error = null
                return
            } else {
                textInputLayout?.error = errorMessageEmail
            }
        }

    }
}