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
import coil.transform.RoundedCornersTransformation
import com.suhail.vajro.R
import com.suhail.vajro.data.Cart
import com.suhail.vajro.data.Product

class ItemAdapter (var cart: List<Cart>?=null): RecyclerView.Adapter<ItemAdapter.ViewHolderClass>() {

    inner class ViewHolderClass(view:View):RecyclerView.ViewHolder(view){
        val imageView :ImageView = view.findViewById(R.id.itemImageViewProduct)
        val itemName : TextView = view.findViewById(R.id.itemProductName)
        val price : TextView = view.findViewById(R.id.itemPriceProduct)
        val addProduct : Button = view.findViewById(R.id.addItemButton)
        val removeProduct : Button = view.findViewById(R.id.removeItemProduct)
        val itemCount : TextView = view.findViewById(R.id.itemCount)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }
//    private val differCallbackCart = object : DiffUtil.ItemCallback<Cart>(){
//        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
//            return oldItem.productId == newItem.productId
//        }
//
//        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
//            return oldItem == newItem
//        }
//    }
    val differ = AsyncListDiffer(this,differCallback)
//    val differCart = AsyncListDiffer(this,differCallbackCart)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_product,parent,false)
        return ViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolderClass, position: Int) {
        val item = differ.currentList[position]
        val cartItems = cart
        holder.imageView.load(item.image){
            placeholder(R.drawable.ic_twotone_hail_24)
            .transformations(RoundedCornersTransformation(30f))
        }
        holder.itemName.text = item.name
        holder.price.text = item.price

        val itemCount = cartItems?.find { it.productId == item.productId }?.quantitiy ?: 0
        holder.itemCount.text = itemCount.toString()
        holder.addProduct.setOnClickListener {
            val count = holder.itemCount.text.toString().toInt()
            holder.itemCount.text = count.plus(1).toString()
            onAddItemClickListner?.let { it(item) }
        }

        holder.removeProduct.setOnClickListener {
            val count = holder.itemCount.text.toString().toInt()
            if (count>0)holder.itemCount.text = count.minus(1).toString()
            onRemoveItemClickListner?.let { it(item) }
        }

    }

    private var onRemoveItemClickListner:((Product)->Unit)?=null

    private var onAddItemClickListner:((Product)->Unit)?=null

    fun setOnClickListner(listner:(Product)->Unit){
            onAddItemClickListner = listner
        }
    fun setOnRemoveListner(listner: (Product) -> Unit){
        onRemoveItemClickListner = listner
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}