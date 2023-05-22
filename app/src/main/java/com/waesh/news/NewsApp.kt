package com.waesh.news

import android.app.Application

class NewsApp: Application() {

    private val newsInterface by lazy {
        RetrofitService.getRetrofitInstance().create(NewsInterface::class.java)
    }

    val repository by lazy {
        Repository(newsInterface)
    }

}