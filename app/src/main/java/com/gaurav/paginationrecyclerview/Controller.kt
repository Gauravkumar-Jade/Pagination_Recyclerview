package com.gaurav.paginationrecyclerview

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class Controller: Application() {

    companion object{
        lateinit var instance: Controller
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}