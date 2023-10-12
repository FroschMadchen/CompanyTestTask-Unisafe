package com.example.testtackunisafe.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtackunisafe.R
import com.example.testtackunisafe.databinding.ItemShoppingListBinding
import com.example.testtackunisafe.domain.model.ShopListConstructor

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
        val binding:ItemShoppingListBinding= ItemShoppingListBinding.inflate(inflater,parent,false)

        binding.root.setOnClickListener(this)
        binding.btnDelete.setOnClickListener(this)
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
    class ShopItemViewHolder(val binding: ItemShoppingListBinding) :
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