package com.example.testtackunisafe.presentation.adapter


import android.graphics.Paint
import android.nfc.Tag
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.testtackunisafe.R
import com.example.testtackunisafe.databinding.ItemProductListBinding
import com.example.testtackunisafe.domain.model.Product

interface ActionListenerProduct{
    fun deleteProduct(item_id: Int)
    fun editProduct(item_id: Int)

}
class SpecificProductListAdapter(
    private val actionListenerProduct: ActionListenerProduct,
    private val productList: ArrayList<Product>
):RecyclerView.Adapter<SpecificProductListAdapter.ItemProductViewHolder>(),View.OnClickListener {
    class ItemProductViewHolder(val binding: ItemProductListBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding:ItemProductListBinding= ItemProductListBinding.inflate(
            inflater,
            parent,
            false)
        binding.titleList
        binding.quantityProduct
        binding.root.setOnClickListener(this)
        binding.btnDelete.setOnClickListener(this)
        return ItemProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ItemProductViewHolder, position: Int) {
        val product = productList[position]
        with(holder.binding){
            btnDelete.tag=product.item_id
            holder.itemView.tag = product.item_id
//            titleList.text = product.name
            quantityProduct.text = product.quantity.toString()

            if (product.crossedOut) {
                titleList.text = product.name
                titleList.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                titleList.text = product.name
            }
        }
    }

    override fun onClick(view: View?) {
        val item_id = view?.tag as Int
        when(view.id){
            R.id.btnDelete -> {
                actionListenerProduct.deleteProduct(item_id)
            }
            else -> {
                actionListenerProduct.editProduct(item_id)
            }
        }

    }

}


