package com.example.servicejob.domain

import com.example.servicejob.Core.Resource
import com.example.servicejob.Data.model.MenuData
import com.example.servicejob.Data.remote.HomeScreenDataSource

class HomeScreenRepoImplement(private val dataSource: HomeScreenDataSource):HomeScreenRepo {
    override suspend fun getMenu(): Resource<List<MenuData>> = dataSource.getMenu()
}