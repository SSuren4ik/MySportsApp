package com.registration.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.registration.databinding.ViewDataInputBinding

class EditTextWithIcon(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    private var binding: ViewDataInputBinding =
        ViewDataInputBinding.inflate(LayoutInflater.from(context), this, true)

    fun setImageResource(resId: Int) {
        binding.iconImage.setImageResource(resId)
    }

    fun setHint(hint: String) {
        binding.editText.hint = hint
    }

    fun setInputType(type: Int) {
        binding.editText.inputType = type
    }

    fun getText(): String {
        return binding.editText.text.toString()
    }
}