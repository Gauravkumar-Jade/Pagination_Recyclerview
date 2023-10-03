package com.gaurav.paginationrecyclerview.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaurav.paginationrecyclerview.model.MovieListResponse
import com.gaurav.paginationrecyclerview.repository.MovieRepository
import com.gaurav.paginationrecyclerview.webservice.NetworkResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor(private val repository: MovieRepository):ViewModel() {


    fun getMovieList(page: Int){

        viewModelScope.launch {
            repository.getMovieList(page)
        }
    }

    val movieList:LiveData<NetworkResults<MovieListResponse>> = repository.movieList



}