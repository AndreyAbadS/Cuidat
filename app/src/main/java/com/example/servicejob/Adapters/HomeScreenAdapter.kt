package com.example.servicejob.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.servicejob.Core.BaseViewHolder
import com.example.servicejob.Data.model.MenuData
import com.example.servicejob.databinding.MenuServicesBinding


class HomeScreenAdapter(private val menuList: List<MenuData>):RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = MenuServicesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeScreenViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is HomeScreenViewHolder -> holder.bind(menuList[position])
        }
    }

    override fun getItemCount(): Int = menuList.size

    private inner class HomeScreenViewHolder(val binding: MenuServicesBinding, val context: Context): BaseViewHolder<MenuData>(binding.root){
        override fun bind(item: MenuData) {
            Glide.with(context).load(item.image_Service).centerCrop().into(binding.ivPhotoMenu)
            binding.tvTitle.text = item.name_Service
        }

    }
}