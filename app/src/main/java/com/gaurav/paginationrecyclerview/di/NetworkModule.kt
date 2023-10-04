package com.gaurav.paginationrecyclerview.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.gaurav.paginationrecyclerview.BuildConfig
import com.gaurav.paginationrecyclerview.Controller
import com.gaurav.paginationrecyclerview.utils.Constants
import com.gaurav.paginationrecyclerview.webservice.APIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {


    @Provides
    fun provideOkHttpClient() = if(BuildConfig.DEBUG){

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val requestInterceptor = Interceptor{ chain ->
            val urls = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", Constants.API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(urls)
                .build()

            return@Interceptor chain.proceed(request)
        }


        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .addInterceptor(ChuckerInterceptor(Controller.instance))
            .build()

    }else{

        OkHttpClient
            .Builder()
            .build()
    }


    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideAPIService(retrofit: Retrofit): APIService{
        return retrofit.create(APIService::class.java)
    }
}