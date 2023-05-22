package com.waesh.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.waesh.news.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        MainViewModelFactory((application as NewsApp).repository)
    }

    private lateinit var binding: ActivityMainBinding
    private val adapter = NewsAdapter(object : ItemClickListener {
        override fun onClick(item: NewsArticles.Article) {
            val intent = Intent(this@MainActivity, WebViewActivity::class.java).apply {
                putExtra("url", item.url)
            }
            startActivity(intent)
        }
    })

    private var totalResults = -1
    private var itemFetched = 0
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getNews(currentPage)

        val recyclerView = binding.rvArticles
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        var previousPosition = 0
        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (totalResults > layoutManager.itemCount && layoutManager.findFirstCompletelyVisibleItemPosition() == layoutManager.itemCount - 10) {
                    if (currentPosition != previousPosition){
                        currentPage++
                        viewModel.getNews(currentPage)
                        //Log.i("kkkCat", "Get news $currentPage")
                    }
                }
                previousPosition = currentPosition
                /*Log.i(
                    "kkkCat",
                    "onScrolled: ${layoutManager.findFirstCompletelyVisibleItemPosition() + 1 } / ${layoutManager.itemCount}"
                )*/
            }
        })

        viewModel.responseBody.observe(this) {
            if (it.status == "ok") {
                totalResults = it.totalResults
                Log.i("kkkCat", "onCreate: $totalResults")
                viewModel.articles.addAll(it.articles)
                adapter.submitList(viewModel.articles)
                itemFetched += viewModel.articles.size
                Log.i("kkkCat", "fetching: $currentPage")
            }
        }
    }
}