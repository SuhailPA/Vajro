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

class CartAdapter : RecyclerView.Adapter<CartAdapter.ViewHolderClass>() {


    inner class ViewHolderClass(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.cartProductImage)
        val itemName: TextView = view.findViewById(R.id.cartProductName)
        val price: TextView = view.findViewById(R.id.totalPrice)
        val addProduct: Button = view.findViewById(R.id.addCartItem)
        val removeProduct: Button = view.findViewById(R.id.cartRemoveItem)
        val itemCount: TextView = view.findViewById(R.id.cartItemCount)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Cart>() {
        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolderClass {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item_layout, parent, false)
        return ViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolderClass, position: Int) {
        val item = differ.currentList[position]
        holder.imageView.load(item.imageUrl) {
            placeholder(R.drawable.ic_twotone_hail_24)
                .transformations(RoundedCornersTransformation(30f))
        }
        holder.itemName.text = item.name
        holder.itemCount.text = item.quantitiy.toString()
        holder.price.text =
            item.quantitiy.times(item.price.filter { it.isDigit() }.toInt()).toString()

        holder.addProduct.setOnClickListener {

        }

        holder.removeProduct.setOnClickListener {

        }

    }

    private var onRemoveItemClickListner: ((Product) -> Unit)? = null

    private var onAddItemClickListner: ((Product) -> Unit)? = null

    fun setOnClickListner(listner: (Product) -> Unit) {
        onAddItemClickListner = listner
    }

    fun setOnRemoveListner(listner: (Product) -> Unit) {
        onRemoveItemClickListner = listner
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}