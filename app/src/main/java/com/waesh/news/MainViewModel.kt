package com.waesh.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {

    val responseBody = repository.responseBody
    val articles: MutableList<NewsArticles.Article> = mutableListOf()

    fun getNews(page: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNews(page)
        }
    }
}

class MainViewModelFactory(private val repository: Repository): ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown VM Class")
    }
}