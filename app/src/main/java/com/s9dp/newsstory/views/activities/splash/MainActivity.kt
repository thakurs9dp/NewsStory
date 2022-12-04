package com.s9dp.newsstory.views.activities.splash

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.s9dp.newsstory.databinding.ActivityMainBinding
import com.s9dp.newsstory.viewmodels.NewsStoryViewModel
import com.s9dp.newsstory.views.fragments.home.ArticlesAdapter
import com.s9dp.newsstory.views.fragments.home.NewsData
import com.s9dp.newsstory.views.utils.classes.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ArticlesAdapter.OnItemClickListener {

    private val viewModel: NewsStoryViewModel by viewModels()
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.writeToLocal(true, "ABC", "email.com")
        getNews()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getNews() {

        val articleAdapter = ArticlesAdapter(this)

        binding.apply {
            rvBreakingNews.apply {
                adapter = articleAdapter
                setHasFixedSize(true)
            }
        }

        viewModel.searchNews("India")
        viewModel.searchNews.observe(this) {
            when (it) {
                is Resource.Success -> {
//                    isLoading = false
//                    paginationProgressBar.visibility = View.INVISIBLE
                    it.data?.let { newsResponse ->
                        articleAdapter.submitList(newsResponse.articles?.toList())

//                        articleAdapter.submitList(newsResponse.articles.toList())
//                        val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
//                        isLastPage = viewModel.searchNewsPage == totalPages
//                        if(isLastPage)
//                            rvBreakingNews.setPadding(0,0,0,0)
                    }
                }
                is Resource.Error -> {
//                  isLoading = true
//                  paginationProgressBar.visibility = View.INVISIBLE
                    it.message?.let { message ->
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
//                  paginationProgressBar.visibility = View.VISIBLE
                }
            }
        }


        lifecycleScope.launchWhenStarted {

            viewModel.readToLocal.collect {
                Log.d("ABC_TEST", "userNAme ${it.fullName}")
                Log.d("ABC_TEST", "email ${it.email}")
                Log.d("ABC_TEST", "isLogged ${it.isLogged}")
            }

        }
    }

    private fun searchNews() {
//        var job: Job? = null
//        etSearch.addTextChangedListener { editable ->
//            job?.cancel()
//            job = MainScope().launch {
//                delay(SEARCH_NEWS_TIME_DELAY)
//                editable?.let {
//                    if (editable.toString().isNotEmpty()) {
//                        viewModel.searchNews(editable.toString())
//                    }
//                }
//            }
//        }
    }

    override fun onItemClick(article: NewsData.Article) {
    }
}