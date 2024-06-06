package com.abdelrahman.moviesapp.data.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import com.abdelrahman.moviesapp.data.network.MovieApiServices
import com.abdelrahman.moviesapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {
    private const val QUERY_LANGUAGE = "en"
    private const val IMAGE_LANGUAGE = "en,null"
    private const val CACHE_SIZE = 1024 * 1024 * 10L // 10 MB
    private const val CACHE_MAX_AGE = 60 * 60  // 1 hour
    private const val CACHE_MAX_STALE = 60 * 60 * 24 * 7 // 7 days

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

            with(networkCapabilities) {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                    else -> false
                }
            }
        } else {
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }
    }
    @Singleton
    @Provides
    fun provideInterceptor(@ApplicationContext context: Context): Interceptor =
        Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", Constants.API_KEY)
                .addQueryParameter("language", QUERY_LANGUAGE)
                .addQueryParameter("include_image_language", IMAGE_LANGUAGE)
                .build()

            val headerName = "Cache-Control"
            val headerValue =
                if (isNetworkAvailable(context)) "public, max-age=$CACHE_MAX_AGE"
                else "public, only-if-cached, max-stale=$CACHE_MAX_STALE"

            val request = chain.request()
                .newBuilder()
                .url(url)
                .header(headerName, headerValue)
                .build()

            chain.proceed(request)
        }


    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor():HttpLoggingInterceptor{
      return  HttpLoggingInterceptor().apply {
            this.level=HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHTTPClint(interceptor:HttpLoggingInterceptor):OkHttpClient{
       return OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
            this.connectTimeout(30, TimeUnit.SECONDS)
            this.readTimeout(20, TimeUnit.SECONDS)
            this.writeTimeout(25, TimeUnit.SECONDS)
        }.build()
    }
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiServices(retrofit: Retrofit): MovieApiServices {
        return retrofit.create(MovieApiServices::class.java)
    }
}