package com.app.foodiehub.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.foodiehub.databinding.LayoutCartItemBinding
import com.app.foodiehub.presentation.model.CartDataModel
import com.app.foodiehub.utils.CartEnum
import com.app.foodiehub.utils.loadUrl

class CartItemAdapter(
    private val list: List<CartDataModel>,
    private val action: (CartDataModel, CartEnum) -> Unit
) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    class ViewHolder(binding: LayoutCartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val add = binding.add
        val minus = binding.minus
        val remove = binding.remove
        val qty = binding.qty

        val image = binding.product.image
        val name = binding.product.name
        val price = binding.product.price
        fun bind(get: CartDataModel, action: (CartDataModel, CartEnum) -> Unit) {
            qty.text = "${get.qty}"
            image.loadUrl(get.data.imageUrl)
            name.text = get.data.name
            price.text = "${get.data.price}"
            add.setOnClickListener { action(get, CartEnum.ADD) }
            minus.setOnClickListener { action(get, CartEnum.SUBTRACT) }
            remove.setOnClickListener { action(get, CartEnum.REMOVE) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.get(position), action)
    }
}