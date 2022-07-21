package com.example.servicejob.ui.services

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.servicejob.Adapters.HomeScreenAdapter
import com.example.servicejob.Core.Resource
import com.example.servicejob.Data.remote.HomeScreenDataSource
import com.example.servicejob.Presentation.HomeScreenViewModel
import com.example.servicejob.Presentation.HomeScreenViewModelFactory
import com.example.servicejob.R
import com.example.servicejob.databinding.FragmentServicesBinding
import com.example.servicejob.domain.HomeScreenRepoImplement


class ServicesFragment : Fragment(R.layout.fragment_services) {
    private lateinit var binding: FragmentServicesBinding
    private val viewModel by viewModels<HomeScreenViewModel> { HomeScreenViewModelFactory(HomeScreenRepoImplement(
        HomeScreenDataSource()
    )) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentServicesBinding.bind(view)

        viewModel.fetchMenu().observe(viewLifecycleOwner){ result->
            when(result){
                is Resource.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success ->{
                    binding.progressBar.visibility = View.GONE
                    binding.rvMenu.adapter = HomeScreenAdapter(result.data)
                }
                is Resource.Failure ->{
                    Toast.makeText(requireContext(),"Ocurrio un error: ${result.exception}",Toast.LENGTH_SHORT).show()
                    Log.d("fail","${result.exception}.toString()")
                }
            }
        }
    }

}