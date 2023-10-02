package com.example.testtackunisafe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtackunisafe.databinding.ItemProductBinding
import com.example.testtackunisafe.recevied_data.ShopListConstructor

interface ActionListener{
    fun deleteList(shopLists:ShopListConstructor)
    fun openList(shopLists: ShopListConstructor)

}
class ShopListAdapter(
    private val actionListener: com.example.testtackunisafe.adapter.ActionListener,
    private val shopLists: List<ShopListConstructor>
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
            btnDelete.tag = shopList
            holder.itemView.tag= shopList

            titleList.text = shopList.name
        }
    }
    override fun getItemCount(): Int {
       return shopLists.size
    }
    class ShopItemViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)
    override fun onClick(p0: View?) {
        p0?.tag as ShopListConstructor
    }
}