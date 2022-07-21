package com.example.servicejob.Data.remote

import com.example.servicejob.Core.Resource
import com.example.servicejob.Data.model.PostServiceData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ServiceScreenDataSource {
    suspend fun getServiceCarpentry(): Resource<List<PostServiceData>> {
        val listService = mutableListOf<PostServiceData>()
        val querySnapshot =
            FirebaseFirestore.getInstance().collection("ServicesCarpentry").get().await()
        for (carpentry in querySnapshot.documents) {
            carpentry.toObject(PostServiceData::class.java)?.let {
                listService.add(it)
            }
        }
        return Resource.Success(listService)
    }
    suspend fun getServiceComputer(): Resource<List<PostServiceData>> {
        val listService = mutableListOf<PostServiceData>()
        val querySnapshot =
            FirebaseFirestore.getInstance().collection("ServicesComputer").get().await()
        for (computer in querySnapshot.documents) {
            computer.toObject(PostServiceData::class.java)?.let {
                listService.add(it)
            }
        }
        return Resource.Success(listService)
    }
}