package com.example.servicejob.domain

import com.example.servicejob.Core.Resource
import com.example.servicejob.Data.model.MenuData

interface HomeScreenRepo {
    suspend fun getMenu(): Resource<List<MenuData>>
}