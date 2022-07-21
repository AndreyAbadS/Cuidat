package com.example.servicejob.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.servicejob.Core.BaseViewHolder
import com.example.servicejob.Data.model.PostServiceData
import com.example.servicejob.databinding.ServicesItemBinding

class ServiceScreenAdapter(private val postList: List<PostServiceData>) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            ServicesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServicesScreenViewHolder(itemBinding,parent.context)
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is ServicesScreenViewHolder -> holder.bind(postList[position])
        }
    }

    override fun getItemCount(): Int = postList.size

    private inner class ServicesScreenViewHolder(
        val binding: ServicesItemBinding,
        val context: Context
    ):BaseViewHolder<PostServiceData>(binding.root){
        override fun bind(item: PostServiceData) {
            Glide.with(context).load(item.profile_name).centerCrop().into(binding.imageService)
            binding.tvNameOferterService.text = item.profile_name
            binding.tvNameService.text = item.description_service
        }

    }
}