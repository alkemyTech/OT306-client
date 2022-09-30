package com.melvin.ongandroid.model.news

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(private val newsApi: NewsService) {
    suspend fun getNews(): NewsResponse =
        withContext(Dispatchers.IO) {
            newsApi.getNews().body() ?: NewsResponse()
        }
}