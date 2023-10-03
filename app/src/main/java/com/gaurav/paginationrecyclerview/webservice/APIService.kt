package com.gaurav.paginationrecyclerview.webservice

import com.gaurav.paginationrecyclerview.model.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {


    @GET("movie/popular")
    suspend fun getMovieList(@Query("page") page: Int): Response<MovieListResponse>
}