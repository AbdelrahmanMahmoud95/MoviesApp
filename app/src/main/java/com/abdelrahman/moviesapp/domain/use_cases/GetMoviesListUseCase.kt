package com.abdelrahman.moviesapp.domain.use_cases

import com.abdelrahman.moviesapp.domain.repositories.Repository
import javax.inject.Inject

class GetMoviesListUseCase @Inject constructor(private val repository: Repository) {

}