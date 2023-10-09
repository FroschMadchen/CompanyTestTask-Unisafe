package com.example.testtackunisafe.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtackunisafe.R
import com.example.testtackunisafe.databinding.ItemProductBinding
import com.example.testtackunisafe.domain.custom_type.ShopListConstructor

interface ActionListener{
    fun deleteList(listId: Int)
    fun openList(listId: Int)

}
class ShopListAdapter(
    private val actionListener: ActionListener,
    private val shopLists: List<ShopListConstructor>,
    )
    : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>(),View.OnClickListener {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding:ItemProductBinding= ItemProductBinding.inflate(inflater,parent,false)

        binding.root.setOnClickListener(this)
        binding.btnDelete.setOnClickListener(this)
        /*
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_product,
            parent,
            false
        )*/

        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopList = shopLists[position]
        with(holder.binding) {
            btnDelete.tag = shopList.listId
            holder.itemView.tag= shopList.listId

            titleList.text = shopList.name
        }
    }
    override fun getItemCount(): Int {
       return shopLists.size
    }
    class ShopItemViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)
    override fun onClick(view: View?) {
        val listId = view?.tag as Int
        when (view.id) {
            R.id.btnDelete -> {
                // Обработка клика на кнопке удаления
                actionListener.deleteList(listId)
            }
            else -> {
                // Обработка клика на элементе списка
                actionListener.openList(listId)
            }
        }
    }
}