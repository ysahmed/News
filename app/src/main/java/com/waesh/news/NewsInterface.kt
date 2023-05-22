package com.waesh.news

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "051a5d4abc924a538345bec333ed0c80"
interface NewsInterface {

    //https://newsapi.org/v2/top-headlines?country=us&apiKey=API_KEY

    @GET("/v2/top-headlines?apiKey=$API_KEY")
    fun getHeadLines(
        @Query("country") country: String,
        @Query("page") page: Int
    ): Call<NewsArticles.NewsResult>
}

object RetrofitService{

    @Volatile
    private var INSTANCE: Retrofit? = null

    fun getRetrofitInstance(): Retrofit {
        //DOUBLE CHECKED LOCKING
        if (INSTANCE == null) {
            synchronized(this)
            {
                if (INSTANCE == null) {

                    INSTANCE = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
        }
        return INSTANCE!!
        //END
    }
}