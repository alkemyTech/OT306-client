package com.melvin.ongandroid.model.testimonial

import com.melvin.ongandroid.utils.ResulState

class OngRepository(private val remoteDataSource: OngRemoteDataSource) {

   suspend fun getTestimonialsList(netWorkResponse: NetWorkResponse<List<Testimonial>>) {
       try {
           remoteDataSource.getTestimonials(netWorkResponse)
       } catch (e: Exception) {
           ResulState.Error(e)
       }
    }
}
