package com.melvin.ongandroid.model.testimonial

import com.melvin.ongandroid.services.RetrofitService
import com.melvin.ongandroid.utils.ResulState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class OngRemoteDataSource {

    suspend fun getTestimonials(netWorkResponse: NetWorkResponse<List<Testimonial>>) =
        withContext(Dispatchers.IO) {
            try {
                val response: Response<TestimonialsResponse> = RetrofitService
                    .instance
                    .create(GetTestimonialsService::class.java)
                    .getTestimonials()
                response.body() ?: emptyList<Testimonial>()
            } catch (e: Exception) {
                ResulState.Error(Exception(e.message))
            }
        }
}


interface NetWorkResponse<T> {
    fun onResponse(value: T?)
}