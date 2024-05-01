package com.app.foodiehub.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.foodiehub.databinding.LayoutBannerItemHomeBinding
import com.app.foodiehub.presentation.model.BannerDataModel
import com.app.foodiehub.utils.loadUrl

class BannerItemAdapterHome(private val list: List<BannerDataModel>) :
    RecyclerView.Adapter<BannerItemAdapterHome.ViewHolder>() {

    class ViewHolder(binding: LayoutBannerItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.bannerImage
        fun bind(get: BannerDataModel) {
            image.loadUrl(get.url)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutBannerItemHomeBinding = LayoutBannerItemHomeBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.get(position))
    }
}