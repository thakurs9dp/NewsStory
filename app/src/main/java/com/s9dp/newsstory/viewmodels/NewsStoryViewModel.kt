package com.s9dp.newsstory.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s9dp.newsstory.BuildConfig
import com.s9dp.newsstory.repository.NewsStoryRepositories
import com.s9dp.newsstory.repository.UserRepository
import com.s9dp.newsstory.views.fragments.home.NewsData
import com.s9dp.newsstory.views.utils.classes.Resource
import com.s9dp.newsstory.views.utils.extention.getCurrentDate
import com.s9dp.newsstory.views.utils.network.ListenNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NewsStoryViewModel @Inject constructor(
    private val newsStoryRepositories: NewsStoryRepositories,
    private val listenNetwork: ListenNetwork,
    private val userRepository: UserRepository
) : ViewModel() {

    // use for check internet connection
    @RequiresApi(Build.VERSION_CODES.M)
    val isConnected: Flow<Boolean> = listenNetwork.isConnected
    val searchNews: MutableLiveData<Resource<NewsData>> = MutableLiveData()
    var searchNewsResponse: NewsData? = null
    var searchNewsPage = 1

    @RequiresApi(Build.VERSION_CODES.M)
    fun searchNews(searchQuery: String) = viewModelScope.launch {
        Log.d("TEST_TEST", "is it is loading view mocel")
        safeSearchNewCall(searchQuery, searchNewsPage)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private suspend fun safeSearchNewCall(searchQuery: String, searchNewsPage: Int) {
        searchNews.postValue(Resource.Loading())
        try {
            isConnected.collect { isNetworkConnected ->
                if (isNetworkConnected) {
                    val response = newsStoryRepositories.getBreakingNews(
                        query = searchQuery,
                        from = "2022-12-02",
                        to = "2022-12-02",
                        sortBy = "popularity",
                        apiKey = BuildConfig.NEWS_API_KEY
                    )

                    Log.d("Test_Test", "Response value is " + response.message())
                    Log.d("Test_Test", "Response value is " + response.isSuccessful)
                    searchNews.postValue(handleSearchNewsResponse(response))
                } else {
                    searchNews.postValue(Resource.Error("No Internet Connection"))
                }
            }
        } catch (ex: Exception) {
            when (ex) {
                is IOException -> searchNews.postValue(Resource.Error("Network Failure"))
                else -> searchNews.postValue(Resource.Error(ex.message.toString()))
            }
        }
    }

    private fun handleSearchNewsResponse(response: Response<NewsData>): Resource<NewsData> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
//              searchNewsPage++
                if (searchNewsResponse == null)
                    searchNewsResponse = resultResponse
                else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    newArticles?.let { oldArticles?.addAll(it) }
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun writeToLocal(isLoggedIn : Boolean, fullName :String, email: String) = viewModelScope.launch {
        userRepository.writeToLocal(isLoggedIn,fullName, email)
    }

    val readToLocal = userRepository.readToLocal

}