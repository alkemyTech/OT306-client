package com.melvin.ongandroid.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.melvin.ongandroid.businesslogic.news.GetNewsUseCase
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.model.news.NewsRepository
import com.melvin.ongandroid.model.news.NewsViewState
import com.melvin.ongandroid.model.news.RetrofitClient
import com.melvin.ongandroid.utils.AppConstants
import com.melvin.ongandroid.utils.Extensions.gone
import com.melvin.ongandroid.utils.Extensions.showToast
import com.melvin.ongandroid.view.adapter.TestimonialAdapter
import com.melvin.ongandroid.view.adapter.HorizontalAdapter
import com.melvin.ongandroid.view.adapter.NewsAdapter
import com.melvin.ongandroid.viewmodel.ActivityViewModel
import com.melvin.ongandroid.viewmodel.ActivityViewModelFactory
import com.melvin.ongandroid.viewmodel.TestimonialsViewModel
import com.melvin.ongandroid.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import com.melvin.ongandroid.viewmodel.news.NewsViewModel
import com.melvin.ongandroid.viewmodel.news.NewsViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var testimonialAdapter: TestimonialAdapter
    private lateinit var newsAdapter: NewsAdapter

    private lateinit var firebaseAnalytic: FirebaseAnalytics

    private val newsViewModel by viewModels<NewsViewModel> {
        NewsViewModelFactory(GetNewsUseCase(NewsRepository(RetrofitClient.webservice)))
    }

    private val viewModel: TestimonialsViewModel by viewModels(
        factoryProducer = { ViewModelFactory() })

    private val viewModels: ActivityViewModel by viewModels(
        factoryProducer = { ActivityViewModelFactory() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalytic = FirebaseAnalytics.getInstance(requireContext())
        setupRvNews()
        newsUpdateUI() // load news
        subscribeUi()

        val adapter = HorizontalAdapter(listOf())
        binding.rvWelcome.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvWelcome.adapter = adapter

        setUpTestimonialRecyclerView()

        viewModels.slides.observe(viewLifecycleOwner) { activitiesList ->
            if (activitiesList != null) {
                adapter.activitiesList = activitiesList
                adapter.notifyDataSetChanged()
                val bundle = Bundle()
                bundle.putString("eventLog", "slider_retrieve_success")
                firebaseAnalytic.logEvent("slider_retrieve_success", bundle)
            } else {
                showToast("Incio - Error general")
                val bundle = Bundle()
                bundle.putString("eventLog", "slider_retrieve_error")
                firebaseAnalytic.logEvent("slider_retrieve_error", bundle)
            }

        }
    }

    // news adapter setup
    private fun setupRvNews() {
        newsAdapter = NewsAdapter()
        binding.rvNews.apply {
            adapter = newsAdapter
            setHasFixedSize(true)
        }
    }

    /*
    Set initial configuration for Testimonial Recycler View
     */
    private fun setUpTestimonialRecyclerView() {
        testimonialAdapter = TestimonialAdapter()
        binding.testimonialsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HomeFragment.context)
            adapter = testimonialAdapter
        }
    }

    // firebase analytics
    private fun logEvents() {
        //last_news_see_more_pressed': Al presionar la flecha "Ver más" en listado de "Últimas novedades"
        val bundle = Bundle()
        bundle.putString("eventLog", "last_news_see_more_pressed")
        firebaseAnalytic.logEvent("last_news_see_more_pressed", bundle)

        //testimonies_see_more_pressed: Al presionar la flecha "Ver más" en listado de "Testimonios"
        val bundleDos = Bundle()
        bundle.putString("eventLog", "testimonies_see_more_pressed")
        firebaseAnalytic.logEvent("testimonies_see_more_pressed", bundleDos)
    }

    /*
    Subscribe all adapters to observe viewModel LiveData
     */
    private fun subscribeUi() {
        subscribeTestimonialAdapter()
    }

    private fun newsUpdateUI() {
        newsViewModel.newsData.observe(viewLifecycleOwner, Observer { viewState ->
            updateUI(viewState)
        })
        newsViewModel.loadNews()
    }

    // news update data
    private fun updateUI(viewState: NewsViewState) {
        when (viewState) {
            is NewsViewState.Loading -> {
                // show progress bar
            }
            is NewsViewState.Content -> {
                if (viewState.content.isEmpty()) {
                    binding.rvNews.gone()
                }
                newsAdapter.setNewsData(viewState.content.subList(0, 4))
                val bundle = Bundle()
                bundle.putString("eventLog", "last_news_retrieve_success")
                firebaseAnalytic.logEvent("last_news_retrieve_success", bundle)
            }
            is NewsViewState.Error -> {
                binding.rvNews.gone()
                // show error message and reload data
                errorSnackBar(AppConstants.SET_MESSAGE) {
                    newsViewModel.loadNews()
                }
                val bundle = Bundle()
                bundle.putString("eventLog", "last_news_retrieve_error")
                firebaseAnalytic.logEvent("last_news_retrieve_error", bundle)
            }
        }
    }

    // error message snackBar
    private fun errorSnackBar(message: String, reloadData: () -> Unit) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(AppConstants.POSITIVE_BUTTON) {
                reloadData()
            }
            .show()
    }

    /*
    Subscribe Testimonial adapter to observe viewModel LiveData
     */
    private fun subscribeTestimonialAdapter() {
        viewModel.testimonialsList.observe(viewLifecycleOwner, Observer { testimonial ->
            if (testimonial != null) {
                testimonialAdapter.submitList(testimonial)
                val bundle = Bundle()
                bundle.putString("eventLog", "testimonios_retrieve_success")
                firebaseAnalytic.logEvent("testimonios_retrieve_success", bundle)
            } else {
                showToast("error al pedir los testimonios")
                val bundle = Bundle()
                bundle.putString("eventLog", "testimonies_retrieve_error")
                firebaseAnalytic.logEvent("testimonies_retrieve_error", bundle)
            }
        })

        viewModel.viewModelScope.launch {
            viewModel.loadTestimonials()
        }
    }
}