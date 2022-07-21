package com.example.servicejob.domain

import com.example.servicejob.Core.Resource
import com.example.servicejob.Data.model.PostServiceData

interface ServiceScreenRepo {
    suspend fun getServiceCarpintry(): Resource<List<PostServiceData>>

    suspend fun getServiceComputer(): Resource<List<PostServiceData>>
}