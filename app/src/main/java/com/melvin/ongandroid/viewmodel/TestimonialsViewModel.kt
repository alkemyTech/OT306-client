package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.model.testimonial.NetWorkResponse
import com.melvin.ongandroid.model.testimonial.OngRepository
import com.melvin.ongandroid.model.testimonial.Testimonial
import kotlinx.coroutines.launch

class TestimonialsViewModel(private val repository: OngRepository) : ViewModel() {

    val testimonialsList = MutableLiveData<List<Testimonial>>()

    init {
        viewModelScope.launch {
            loadTestimonials()
        }
    }

    private suspend fun loadTestimonials() {
        repository.getTestimonialsList(object : NetWorkResponse<List<Testimonial>> {
            override fun onResponse(value: List<Testimonial>?) {
                testimonialsList.value = value!!
            }
        })
    }
}

// new viewModel Factory Instance
class TestimonailViewModelFactory(private val repo: OngRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(OngRepository::class.java).newInstance(repo)
    }
}