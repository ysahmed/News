package com.waesh.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val newsApi: NewsInterface) {

    //val apiResponse:MutableLiveData<Response<NewsArticles>> = MutableLiveData()
    val responseBody: MutableLiveData<NewsArticles.NewsResult> = MutableLiveData()

    fun getNews(page: Int) {
        val news = newsApi.getHeadLines("us", page)
        news.enqueue(object : Callback<NewsArticles.NewsResult> {
            override fun onResponse(
                call: Call<NewsArticles.NewsResult>,
                response: Response<NewsArticles.NewsResult>
            ) {
                if (response.body() != null) {
                    responseBody.postValue(response.body())
                }

                //Log.i("kkkCat", "Page: $page")
            }

            override fun onFailure(call: Call<NewsArticles.NewsResult>, t: Throwable) {
                Log.i("kkkCat", "Fetching news failure.")
            }
        })
    }
}