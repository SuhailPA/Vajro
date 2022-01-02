package com.suhail.vajro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.suhail.vajro.R
import com.suhail.vajro.data.Product
import org.w3c.dom.Text

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolderClass>() {

    inner class ViewHolderClass(view:View):RecyclerView.ViewHolder(view){
        val imageView :ImageView = view.findViewById(R.id.itemImageViewProduct)
        val itemName : TextView = view.findViewById(R.id.itemProductName)
        val price : TextView = view.findViewById(R.id.itemPriceProduct)
        val addProduct : Button = view.findViewById(R.id.addItemButton)
        val removeProduct : Button = view.findViewById(R.id.removeItemProduct)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_product,parent,false)
        return ViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolderClass, position: Int) {
        val item = differ.currentList[position]
        holder.imageView.load(item.image){
            placeholder(R.drawable.ic_twotone_hail_24)
            .transformations(RoundedCornersTransformation(30f))
        }
        holder.itemName.text = item.name
        holder.price.text = item.price

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}