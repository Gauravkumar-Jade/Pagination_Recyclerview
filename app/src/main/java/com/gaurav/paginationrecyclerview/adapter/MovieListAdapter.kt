package com.gaurav.paginationrecyclerview.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.gaurav.paginationrecyclerview.R
import com.gaurav.paginationrecyclerview.databinding.RawMovieListBinding
import com.gaurav.paginationrecyclerview.model.Result
import com.gaurav.paginationrecyclerview.utils.Constants
import javax.inject.Inject

class MovieListAdapter @Inject constructor():RecyclerView.Adapter<MovieListAdapter.MovieListHolder>() {

    private var mMovieList:List<Result>? = null


    fun bindData(movieList:List<Result>?){
        this.mMovieList = movieList
        notifyDataSetChanged()
    }

    class MovieListHolder(val binding: RawMovieListBinding):ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(result: Result?){
            val img_link = Constants.POSTER_BASE_URL.plus(result?.poster_path)

            // Data Binding
            binding.setVariable(BR.movie,result)
            binding.executePendingBindings()

            binding.apply {

                Glide.with(itemView.context)
                    .load(img_link)
                    .override(Target.SIZE_ORIGINAL)
                    .error(R.drawable.baseline_image_error)
                    .into(ivMovie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        return MovieListHolder(RawMovieListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun getItemCount(): Int {
        return mMovieList?.size ?: 0
    }

    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        val movie = mMovieList?.get(position)
        holder.bind(movie)
    }
}