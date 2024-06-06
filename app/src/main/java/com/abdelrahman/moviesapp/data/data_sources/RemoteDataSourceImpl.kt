package com.abdelrahman.moviesapp.data.data_sources


import com.abdelrahman.paymob.base.data.network.ApiServices
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiServices: ApiServices
) : RemoteDataSource {


}