package com.melvin.ongandroid.model.news

import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NewsService {
    @GET("api/news")
    suspend fun getNews(): Response<NewsResponse>
}

object RetrofitClient {
    val webservice by lazy {
        Retrofit.Builder()
            .baseUrl("https://ongapi.alkemy.org/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(NewsService::class.java)
    }
}