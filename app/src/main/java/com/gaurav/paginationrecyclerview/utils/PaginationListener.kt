package com.gaurav.paginationrecyclerview.utils

import android.util.Log
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class PaginationListener(mLayoutManager: LinearLayoutManager): RecyclerView.OnScrollListener() {


    private var isScrolling:Boolean = false


    /**
     * Supporting only LinearLayoutManager.
     */
    val layoutManager: LinearLayoutManager = mLayoutManager


    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
            isScrolling = true
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (isScrolling && !isLastPage()){
            if(visibleItemCount.plus(firstVisibleItemPosition) == totalItemCount) {
                isScrolling = false
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean

}