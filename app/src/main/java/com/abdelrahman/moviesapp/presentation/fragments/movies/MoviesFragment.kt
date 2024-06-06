package com.abdelrahman.moviesapp.presentation.fragments.movies

import android.os.Bundle
import android.view.View
import com.abdelrahman.moviesapp.databinding.FragmentMoviesBinding
import com.abdelrahman.moviesapp.presentation.fragments.BaseFragment
import com.abdelrahman.paymob.base.presentation.ui_handlers.HandlerRequest
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(FragmentMoviesBinding::inflate),
    HandlerRequest {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdaptor()
        handleUI()
        observeData()
    }

    override fun startRequest() {
    }

    override fun endRequest(errorMessage: String?) {
    }

    override fun setupAdaptor() {
    }

    override fun handleUI() {
    }

    override fun observeData() {

    }


}
