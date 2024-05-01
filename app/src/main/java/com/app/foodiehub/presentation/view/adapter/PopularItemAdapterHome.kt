package com.app.foodiehub.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.foodiehub.databinding.LayoutPopularItemHomeBinding
import com.app.foodiehub.presentation.model.ProductDataModel
import com.app.foodiehub.utils.loadUrl

class PopularItemAdapterHome(
    private val list: List<ProductDataModel>,
    val itemClickListener: (ProductDataModel) -> Unit,
    val onAdd: (ProductDataModel) -> Unit
) : RecyclerView.Adapter<PopularItemAdapterHome.ViewHolder>() {

    class ViewHolder(binding: LayoutPopularItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val product = binding.product.root
        private val name = binding.product.name
        private val image = binding.product.image
        private val price = binding.product.price
        private val addBtn = binding.addCartBtn

        fun bind(
            get: ProductDataModel,
            itemClickListener: (ProductDataModel) -> Unit,
            onAdd: (ProductDataModel) -> Unit
        ) {
            name.setText(get.name)
            price.setText("${get.price}")
            image.loadUrl(get.imageUrl)
            product.setOnClickListener {
                itemClickListener(get)
            }
            addBtn.setOnClickListener { onAdd(get) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutPopularItemHomeBinding =
            LayoutPopularItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(list.get(position), itemClickListener, onAdd)

}