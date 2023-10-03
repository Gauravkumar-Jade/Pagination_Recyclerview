package com.gaurav.paginationrecyclerview.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gaurav.paginationrecyclerview.model.MovieListResponse
import com.gaurav.paginationrecyclerview.webservice.APIService
import com.gaurav.paginationrecyclerview.webservice.NetworkResults
import com.gaurav.paginationrecyclerview.webservice.safeApiCall

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: APIService) {

    private var _movieList = MutableLiveData<NetworkResults<MovieListResponse>>()
    val movieList:LiveData<NetworkResults<MovieListResponse>> get() = _movieList


    suspend fun getMovieList(page:Int){
        val movieData = safeApiCall(Dispatchers.IO){
            apiService.getMovieList(page)
        }

        _movieList.postValue(movieData)
    }
}