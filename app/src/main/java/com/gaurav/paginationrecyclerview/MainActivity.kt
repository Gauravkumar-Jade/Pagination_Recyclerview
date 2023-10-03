package com.gaurav.paginationrecyclerview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaurav.paginationrecyclerview.adapter.MovieListAdapter
import com.gaurav.paginationrecyclerview.databinding.ActivityMainBinding
import com.gaurav.paginationrecyclerview.utils.PaginationListener
import com.gaurav.paginationrecyclerview.viewModel.MovieListViewModel
import com.gaurav.paginationrecyclerview.webservice.NetworkResults
import com.gaurav.paginationrecyclerview.model.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    private lateinit var viewModel:MovieListViewModel

    private var movieList:ArrayList<Result>? = null
    private var isLastPage:Boolean = false

    private val pageStart: Int = 1
    private var currentPage: Int = pageStart
    private var totalPage:Int = 500

    private var layoutManager: LinearLayoutManager? = null

    @Inject
    lateinit var mAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVariable()
        observeData()
    }

    private fun initVariable() {
        movieList = ArrayList()
        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        layoutManager = LinearLayoutManager(this)
        binding.rvMovieList.layoutManager = layoutManager
        binding.rvMovieList.adapter = mAdapter
        binding.rvMovieList.setHasFixedSize(true)

        loadPage()
        setPagination()
    }

    private fun loadPage() {
        viewModel.getMovieList(currentPage)
    }

    private fun observeData(){
        viewModel.movieList.observe(this){
            when(it){

                is NetworkResults.Success -> {
                    val movie = it.data?.results
                    movieList?.addAll(movie!!)
                    binding.loader.visibility = View.GONE
                    mAdapter.bindData(movieList)

                }
                is NetworkResults.Error -> {
                    showToast(it.code, it.message.toString())
                    binding.loader.visibility = View.GONE
                }
                is NetworkResults.Exception -> {
                    showToast(message = it.e.message!!)
                    binding.loader.visibility = View.GONE
                }
            }
        }
    }

    private fun setPagination() {
        binding.rvMovieList.addOnScrollListener(object:PaginationListener(layoutManager!!){
            override fun loadMoreItems() {
                if(currentPage != totalPage){
                    binding.loader.visibility = View.VISIBLE
                    currentPage+=1
                    lifecycleScope.launch {
                        delay(1000)
                        loadPage()
                    }
                }else{
                    isLastPage = true
                }

            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }
        })
    }


    private fun showToast(code:Int = 0, message: String){
        if(code != 0)
            Toast.makeText(this,"$code - $message", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

}