package com.example.servicejob.Presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.servicejob.Core.Resource
import com.example.servicejob.domain.HomeScreenRepo
import kotlinx.coroutines.Dispatchers

class HomeScreenViewModel(private val repo:HomeScreenRepo):ViewModel() {
    fun fetchMenu() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getMenu())
        }catch (e:Exception){
            emit(Resource.Failure(e))
        }
    }
}

class HomeScreenViewModelFactory(private val repo:HomeScreenRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeScreenRepo::class.java).newInstance(repo)
    }
}