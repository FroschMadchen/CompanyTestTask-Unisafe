package com.example.testtackunisafe.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.testtackunisafe.databinding.ItemProductListBinding
import com.example.testtackunisafe.domain.model.Product

class SpecificProductListAdapter(
    private val productList: ArrayList<Product>
):RecyclerView.Adapter<SpecificProductListAdapter.ItemProductViewHolder>() {
    class ItemProductViewHolder(val binding: ItemProductListBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding:ItemProductListBinding= ItemProductListBinding.inflate(inflater,parent,false)
        binding.titleList
        binding.btnDelete.setOnClickListener(this)
        return ItemProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ItemProductViewHolder, position: Int) {
        val product = productList[position]
        with(holder.binding){
            titleList.text = product.name

        }
    }

}

private fun Button.setOnClickListener(specificProductListAdapter: SpecificProductListAdapter) {

}
