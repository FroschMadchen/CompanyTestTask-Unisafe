package com.example.testtackunisafe.presentation.adapter


import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtackunisafe.R
import com.example.testtackunisafe.databinding.ItemProductListBinding
import com.example.testtackunisafe.domain.model.loadingReadyList.Item

interface ActionListenerProduct{
    fun crossItProduct(id: Int)
    fun deleteProduct(id: Int)

}
class SpecificProductListAdapter(
    private val actionListenerProduct: ActionListenerProduct,
    private val productList: MutableList<Item>
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
        binding.quantityProductEdit
        binding.btnDelete.setOnClickListener(this)
        binding.btnCrossItOff.setOnClickListener(this)
        return ItemProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ItemProductViewHolder, position: Int) {
        val product = productList[position]
        with(holder.binding){
            btnDelete.tag=product.id
            btnCrossItOff.tag= product.id
//            holder.itemView.tag = product.id
             titleList.text = product.name
            quantityProduct.text = product.created.toString()
            quantityProductEdit.text


            if (product.is_crossed) {
                titleList.text = product.name
                titleList.paintFlags = titleList.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                Log.i("TextCrossedOut","${product.id} text is crossed out*")
            } else {
                titleList.text = product.name
                titleList.paintFlags = titleList.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                Log.i("NOTTextCrossedOut"," ${product.id} text is crossed NOT out*")
            }
        }
    }

    override fun onClick(view: View?) {
        val id = view?.tag as Int
        when(view.id){
            R.id.btnCrossItOff -> {
                actionListenerProduct.crossItProduct(id)
            }
            else -> {
                actionListenerProduct.deleteProduct(id)
            }
        }


    }

}


