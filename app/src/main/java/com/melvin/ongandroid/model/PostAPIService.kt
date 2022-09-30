package com.melvin.ongandroid.model

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PostAPIService {

    @POST("api/contacts")
    suspend fun sendContactMessageToAPI (@Body contactMessage:ContactMessageDto):Response<ContactMessageDto>
}