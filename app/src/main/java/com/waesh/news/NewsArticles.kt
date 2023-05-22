package com.waesh.news

object NewsArticles {
    data class NewsResult(
        val articles: MutableList<Article>,
        val status: String,
        val totalResults: Int
    )

    data class Article(
        val author: String,
        val content: String,
        val description: String,
        val publishedAt: String,
        val source: Source,
        val title: String,
        val url: String,
        val urlToImage: String
    )

    data class Source(
        val id: String,                        
        val name: String
    )
}