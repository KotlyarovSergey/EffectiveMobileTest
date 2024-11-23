package com.ksv.effectivemobiletest.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.ksv.effectivemobiletest.data.RetrofitInstance
import com.ksv.effectivemobiletest.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filterButton.setOnClickListener {
            lifecycleScope.launch {
                try {

//                val response = RetrofitInstance.getApi.getCoursesResponse(221764)
//                val response = RetrofitInstance.getApi.getCoursesByTag(55)
//                    val response = RetrofitInstance.getApi.getCourses()
//                    val response = RetrofitInstance.getApi.getCoursesList(1)
                    val response = RetrofitInstance.getApi.getCourseReviewSummary(221574)


//                val response = RetrofitInstance.getCourseApiResponse.getDataPhotosResponse(123)
                    if (response.isSuccessful) {
                        val reviewSummaryResponse = response.body()
                        if (reviewSummaryResponse != null) {
                            val rating = reviewSummaryResponse.summaries.first().average
                            val txt = rating.toString()
                            binding.searchEdit.setText(txt)
                        } else {
                            binding.searchEdit.text = null
                        }
                    } else {
                        val txt = response.message()
                        binding.searchEdit.setText(txt)

                    }
                } catch (ex: Exception) {
                    Log.d("ksv", ex.message.toString())
                    val txt = ex.message.toString()
                    binding.searchEdit.setText(txt)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}