package com.ikhsan.storyeapp.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.ikhsan.storyapp.R
import com.ikhsan.storyapp.base.BaseCommonFunction
import com.ikhsan.storyapp.base.FragmentLifecycleAware
import com.ikhsan.storyapp.base.InfoDialog
import com.ikhsan.storyapp.util.Inflate

abstract class BaseFragment<out VB: ViewBinding>(private val inflate: Inflate<VB>): Fragment(), BaseCommonFunction {

    private var _binding: VB? = null
    protected val bind get() = _binding!!

    protected val mainScope by lazy { FragmentLifecycleAware() }
    private var messageDialog: InfoDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(mainScope)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        initObserver()
    }

    override fun onPause() {
        super.onPause()
        loadingDialog.dismiss()
    }

    override fun onDestroyView() {
        showLoadingDialog(false)
        messageDialog?.dismiss()
        loadingDialog.dismiss()
        messageDialog = null
        _binding = null
        super.onDestroyView()
    }


    override fun initView() {}

    override fun initListener() {}

    override fun initObserver() {}

    protected fun gooTo(directions: NavDirections) = findNavController().navigate(directions)

    protected fun setStatusBarColor(color: Int, isLightStatusBars: Boolean){
        val view = requireActivity().window
        view.statusBarColor = ContextCompat.getColor(requireContext(), color)
        WindowInsetsControllerCompat(view, view.decorView).isAppearanceLightStatusBars = isLightStatusBars
    }

    private val loadingDialog: Dialog by lazy {
        Dialog(requireContext()).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.custom_loading, null)
            setContentView(view)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window!!.attributes = WindowManager.LayoutParams().apply {
                copyFrom(window!!.attributes)
                width = WindowManager.LayoutParams.WRAP_CONTENT
                height = WindowManager.LayoutParams.WRAP_CONTENT
                gravity = Gravity.CENTER
            }
            setCancelable(false)
        }
    }

    protected fun showLoadingDialog(status: Boolean) {
        if (status) loadingDialog.show() else loadingDialog.dismiss()
    }

    override fun String?.makeToast() = Toast.makeText(requireContext(), this ?: "", Toast.LENGTH_SHORT).show()

}