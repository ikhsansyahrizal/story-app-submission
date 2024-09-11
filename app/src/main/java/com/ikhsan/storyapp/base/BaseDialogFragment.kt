package com.ikhsan.storyapp.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.ikhsan.storyapp.util.Inflate

abstract class BaseDialogFragment<out VB : ViewBinding>(private val inflate: Inflate<VB>) : DialogFragment(), CommonInitialization,
    CommonToast {

    private var _binding: VB? = null
    protected val bind get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater)
        initView()
        initListener()
        return bind.root
    }


    override fun initView() {}
    override fun initListener() {}

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
    }

    override fun String?.makeToast() {
        Toast.makeText(requireContext(), this, Toast.LENGTH_SHORT).show()
    }

    fun View.listener(callback: () -> Unit) {
        this.setOnClickListener {
            callback()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}