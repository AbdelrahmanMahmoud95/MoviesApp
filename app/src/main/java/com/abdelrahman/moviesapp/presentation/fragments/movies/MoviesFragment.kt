package com.abdelrahman.moviesapp.presentation.fragments.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.abdelrahman.moviesapp.databinding.FragmentMoviesBinding
import com.abdelrahman.moviesapp.presentation.base.BaseFragment

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(FragmentMoviesBinding::inflate){
    override val viewModel: MoviesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}
