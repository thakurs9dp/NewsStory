package com.s9dp.newsstory.data.services

import com.s9dp.newsstory.views.fragments.home.NewsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//https://rapidapi.com/blog/rapidapi-featured-news-apis/
//https://newsapi.org/v2/everything?q=tesla&from=2022-11-03&sortBy=publishedAt&apiKey=c1832d0d2b4f48e7a2f5bf23e8d4b71c
interface ApiServices {
    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String,
    ): Response<NewsData>
}