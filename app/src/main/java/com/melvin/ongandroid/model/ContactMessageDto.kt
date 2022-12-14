package com.melvin.ongandroid.model

import com.google.gson.annotations.SerializedName

// Dto = Data Transfer Object , contiene el modelo de datos que debe hacerse a la llamada POST de la seccion contacto
data class ContactMessageDto(
    @SerializedName("id") val id:Int? = 0,
    @SerializedName("name") val nameAndLastName:String?= "",
    @SerializedName("email") val email:String? = "",
    @SerializedName("message") val message:String? = "",
)