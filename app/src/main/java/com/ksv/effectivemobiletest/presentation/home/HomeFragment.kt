package com.ksv.effectivemobiletest.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ksv.effectivemobiletest.R
import com.ksv.effectivemobiletest.databinding.FragmentHomeBinding
import com.ksv.effectivemobiletest.entity.CourseItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    private val recyclerListAdapter = CourseListAdapter { onDetailsClick(it) }
    private val recyclerPagingAdapter = CoursesPagedListAdapter { onDetailsClick(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

//        binding.recycler.adapter = recyclerListAdapter
        binding.recycler.adapter = recyclerPagingAdapter

        binding.sortButton.setOnClickListener {
            BottomSortingFragment().show(parentFragmentManager, BottomSortingFragment.TAG)
        }

//        viewModel.courses.onEach { courses ->
//            recyclerListAdapter.submitList(courses)
//        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.pagedCourses.onEach {
            recyclerPagingAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.sortingBy.onEach { sortingType ->
            binding.sortButton.text =
                when (sortingType) {
                    SortingTypes.DATE_DOWN -> getString(R.string.sorting_by_date)
                    SortingTypes.RATING_DOWN -> getString(R.string.sorting_by_rating)
                    SortingTypes.POPULARITY_DOWN -> getString(R.string.sorting_by_popularity)
                    SortingTypes.PRICE_UP -> getString(R.string.sorting_by_price_up)
                    SortingTypes.PRICE_DOWN -> getString(R.string.sorting_by_price_down)
                }
        }.launchIn((viewLifecycleOwner.lifecycleScope))

    }

    private fun onDetailsClick(index: Int) {
        Toast.makeText(
            requireContext(),
            "Переходим на курс под номером $index",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onDetailsClick(course: CourseItem) {
        Toast.makeText(
            requireContext(),
            "Переходим на курс #${course.id}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}