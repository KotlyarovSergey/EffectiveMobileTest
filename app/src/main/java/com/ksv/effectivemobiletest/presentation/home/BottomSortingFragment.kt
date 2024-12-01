package com.ksv.effectivemobiletest.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ksv.effectivemobiletest.R
import com.ksv.effectivemobiletest.databinding.FragmentBottomSortingBinding

class BottomSortingFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentBottomSortingBinding
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSortingBinding.bind(
            inflater.inflate(
                R.layout.fragment_bottom_sorting,
                container
            )
        )
        return binding.root
    }

    companion object {
        const val TAG = "BottomSortingFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (homeViewModel.sortingBy.value) {
            SortingTypes.DATE_DOWN -> {
                binding.radioDate.isChecked = true
            }
            SortingTypes.RATING_DOWN -> {
                binding.radioRating.isChecked = true
            }
            SortingTypes.POPULARITY_DOWN -> {
                binding.radioPopularity.isChecked = true
            }
            SortingTypes.PRICE_UP -> {
                binding.radioPriceUp.isChecked = true
            }
            SortingTypes.PRICE_DOWN -> {
                binding.radioPriceDown.isChecked = true
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.radioDate.id -> {
                    homeViewModel.changeSort(SortingTypes.DATE_DOWN)
                }

                binding.radioRating.id -> {
                    homeViewModel.changeSort(SortingTypes.RATING_DOWN)
                }

                binding.radioPopularity.id -> {
                    homeViewModel.changeSort(SortingTypes.POPULARITY_DOWN)
                }

                binding.radioPriceUp.id -> {
                    homeViewModel.changeSort(SortingTypes.PRICE_UP)
                }

                binding.radioPriceDown.id -> {
                    homeViewModel.changeSort(SortingTypes.PRICE_DOWN)
                }
            }
            dismiss()
        }
    }
}