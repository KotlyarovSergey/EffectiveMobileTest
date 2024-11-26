package com.ksv.effectivemobiletest.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksv.effectivemobiletest.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val recyclerAdapter = CourseCardAdapter { onDetailsClick(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = recyclerAdapter

        viewModel.courses.onEach { courses ->
            recyclerAdapter.setDate(courses)
//            val txt = courses.joinToString("\n") { "${it.rating.toString().substring(0..2)} ${it.name}" }
//            val txt = courses.joinToString("\n") { "DT: ${dateConverter(it.date)}" }
//            binding.sortButton.text = txt
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onDetailsClick(index: Int) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}