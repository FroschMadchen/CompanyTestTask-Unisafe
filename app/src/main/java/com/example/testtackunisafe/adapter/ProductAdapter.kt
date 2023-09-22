package com.example.testtackunisafe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testtackunisafe.R
import com.example.testtackunisafe.databinding.ItemProductBinding

class ProductAdapter : ListAdapter<Shop, ProductAdapter.Holder>(Comparator()){
    class  Holder (view: View): RecyclerView.ViewHolder(view){
        private  val binding = ItemProductBinding.bind(view)

        fun bind(shop: Shop)= with(binding){
            titleTV.text = shop.name
            descriptionTV.text = shop.created
        }
    }

    class Comparator : DiffUtil.ItemCallback<Shop>(){
        override fun areItemsTheSame(oldItem: Shop, newItem: Shop): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Shop, newItem: Shop): Boolean {
            return oldItem == newItem
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))

    }


}