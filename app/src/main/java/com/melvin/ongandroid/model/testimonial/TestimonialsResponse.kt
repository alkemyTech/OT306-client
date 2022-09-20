package com.melvin.ongandroid.model.testimonial

import com.google.gson.annotations.SerializedName
import com.melvin.ongandroid.model.testimonial.Testimonial

class TestimonialsResponse(
    @SerializedName("success")
    val success : Boolean,
    @SerializedName("data")
    val testimonialsList:List<Testimonial>,
    @SerializedName("message")
    val message:String
    )
