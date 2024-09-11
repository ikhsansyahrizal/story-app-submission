package com.ikhsan.storyapp.base

import com.ikhsan.storyapp.databinding.DialogCommonCoreBinding

class InfoDialog(
    private val title: String,
    private val buttonText: String,
    private val icon: Int,
    private val ok: (() -> Unit)? = null
) :
    BaseDialogFragment<DialogCommonCoreBinding>(DialogCommonCoreBinding::inflate) {

    override fun initView() {
        bind.tvCommonTitle.text = title
        bind.btnCommonOk.text = buttonText
        bind.imgCommon.setImageResource(icon)
    }

    override fun initListener() {
        bind.btnCommonOk.listener {
            ok?.invoke()
        }
    }
}