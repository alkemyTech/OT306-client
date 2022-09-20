package com.melvin.ongandroid.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.model.testimonial.OngRemoteDataSource
import com.melvin.ongandroid.model.testimonial.OngRepository
import com.melvin.ongandroid.view.adapter.TestimonialAdapter
import com.melvin.ongandroid.viewmodel.TestimonialsViewModel
import com.melvin.ongandroid.view.adapter.ActivitiesAdapter
import com.melvin.ongandroid.view.adapter.NewsAdapter
import com.melvin.ongandroid.viewmodel.TestimonailViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var testimonialAdapter: TestimonialAdapter
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var activitiesAdapter: ActivitiesAdapter

    // viewModel instance with new viewModel factory
    private val viewModel by viewModels<TestimonialsViewModel> {
        TestimonailViewModelFactory(OngRepository(OngRemoteDataSource()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActivitiesRecyclerView()
        setUpTestimonialRecyclerView()
        setupNewsRecyclerView()
        subscribeUi()
    }

    // setup recyclerView News
    private fun setupNewsRecyclerView() {
        newsAdapter = NewsAdapter(emptyList())
        binding.rvNews.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
            adapter = newsAdapter
        }
    }

    // setup recyclerView Activities
    private fun setupActivitiesRecyclerView() {
        activitiesAdapter = ActivitiesAdapter(emptyList())
        binding.rvWelcome.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = activitiesAdapter
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


    /*
    Subscribe all adapters to observe viewModel LiveData
     */
    private fun subscribeUi() {
        subscribeTestimonialAdapter()
    }

    /*
    Subscribe Testimonial adapter to observe viewModel LiveData
     */
    private fun subscribeTestimonialAdapter() {
        viewModel.testimonialsList.observe(viewLifecycleOwner){ testimonial ->
            if (testimonial != null){
                testimonialAdapter.submitList(testimonial)
            }
            else{
                //TODO : Show error general de la API
            }

        }
    }
}