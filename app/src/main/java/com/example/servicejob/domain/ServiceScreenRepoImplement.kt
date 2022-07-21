package com.example.servicejob.domain

import com.example.servicejob.Core.Resource
import com.example.servicejob.Data.model.PostServiceData
import com.example.servicejob.Data.remote.ServiceScreenDataSource

class ServiceScreenRepoImplement(private val dataSource: ServiceScreenDataSource):ServiceScreenRepo {
    override suspend fun getServiceCarpintry(): Resource<List<PostServiceData>> = dataSource.getServiceCarpentry()
    
    override suspend fun getServiceComputer(): Resource<List<PostServiceData>> = dataSource.getServiceComputer()

}