package com.example.servicejob.Data.remote

import com.example.servicejob.Core.Resource
import com.example.servicejob.Data.model.MenuData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {
    suspend fun getMenu(): Resource<List<MenuData>> {
        val listMenu = mutableListOf<MenuData>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("menuServices").get().await()
        for (menu in querySnapshot.documents) {
            menu.toObject(MenuData::class.java)?.let {
                listMenu.add(it)
            }
        }
        return Resource.Success(listMenu)
    }
}