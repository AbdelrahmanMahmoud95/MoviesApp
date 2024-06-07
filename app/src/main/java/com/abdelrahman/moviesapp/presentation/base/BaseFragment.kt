package com.abdelrahman.moviesapp.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.abdelrahman.moviesapp.R
import com.abdelrahman.moviesapp.utils.Constants
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    abstract val viewModel: BaseViewModel?

    private var mediator: TabLayoutMediator? = null

    private var snackbar: Snackbar? = null

    protected val detailId by lazy { arguments?.getInt(Constants.DETAIL_ID)!! }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    protected fun collectFlows(collectors: List<KSuspendFunction0<Unit>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collectors.forEach { collector ->
                    launch {
                        collector()
                    }
                }
            }
        }
    }

    protected fun showSnackbar(
        message: String,
        indefinite: Boolean = true,
        actionText: String? = null,
        anchor: Boolean = false,
        action: (() -> Unit)? = null
    ) {
        val view = activity?.window?.decorView?.rootView
        val length = if (indefinite) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG
        val snackbar = view?.let { Snackbar.make(it, message, length) }

        if (action != null) snackbar?.setAction(actionText) { action() }
        if (anchor) snackbar?.setAnchorView(R.id.bottom_navigation)

        this.snackbar = snackbar
        this.snackbar?.show()
    }

    override fun onPause() {
        super.onPause()
        snackbar?.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        with(requireActivity() as AppCompatActivity) {
            if (actionBar != null) setSupportActionBar(null)
        }

        mediator?.detach()
        mediator = null

        _binding = null
    }
}