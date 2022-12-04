package com.s9dp.newsstory.repository

import com.s9dp.newsstory.data.services.ApiServices
import com.s9dp.newsstory.views.fragments.home.NewsData
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsStoryRepositories @Inject constructor(
    private val apiService: ApiServices
) {

    suspend fun getBreakingNews(
        query: String,
        from: String,
        to: String,
        sortBy: String,
        apiKey: String
    ): Response<NewsData> {
        return apiService.getNews(query, from, to, sortBy, apiKey)
    }

}