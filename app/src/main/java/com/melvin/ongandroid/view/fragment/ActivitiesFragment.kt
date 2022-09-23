package com.melvin.ongandroid.view.fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.databinding.FragmentActivitiesBinding
import com.melvin.ongandroid.model.Activity
import com.melvin.ongandroid.view.adapter.ActivitiesAdapter


class ActivitiesFragment : Fragment() {
    private var _binding: FragmentActivitiesBinding? = null
    private val binding get() = _binding!!
    private lateinit var activitiesAdapter: ActivitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActivitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActivitiesRecyclerView()
        subscribeUi()

    }

    /*
    Set initial configuration for Activities Recycler View
     */
    private fun setUpActivitiesRecyclerView() {
        activitiesAdapter = ActivitiesAdapter()
        binding.recyclerViewActivities.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ActivitiesFragment.context,LinearLayoutManager.HORIZONTAL, false )
            adapter = activitiesAdapter
        }
    }

    /*
   Subscribe Activities adapter to observe viewModel LiveData
    */
    private fun subscribeUi() {
        //TODO replace this with Api Data
        val previewList = listOf<Activity>(
            Activity(title = "Actividad de prueba 1", description = "Descripción de la actividad de prueba 1 para ver como se ve corriendo en el emulador, un saludo aca desde Villa Maipu."),
            Activity(title = "Actividad de prueba 2", description = "Descripción de la actividad de prueba 2 esta va a ser más corta, se me acaba rápido la imaginación.")
        )
        activitiesAdapter.submitList(previewList)
    }

}