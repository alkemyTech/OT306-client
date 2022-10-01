package com.melvin.ongandroid.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentNovedadesBinding
import com.melvin.ongandroid.view.adapter.NovedadesAdapter

class NovedadesFragment : Fragment() {
    private var _binding: FragmentNovedadesBinding? = null
    private val binding get() = _binding!!
    private lateinit var novedadesAdapter: NovedadesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNovedadesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NovedadesAdapter(listOf())
        binding.rvNovedades.layoutManager = GridLayoutManager(context, 3)
        binding.rvNovedades.adapter = adapter
    }
}