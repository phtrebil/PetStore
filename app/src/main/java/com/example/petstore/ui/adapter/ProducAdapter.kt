package com.example.petstore.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.petstore.databinding.ProductLayoutBinding
import com.example.petstore.model.Product

class ProducAdapter(
    private val context: Context,
    var onClickItem: (product:Product) -> Unit = {},
    products: List<Product> = emptyList()
): RecyclerView.Adapter<ProducAdapter.ProductViewHolder>() {

    private val products = products.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ProductLayoutBinding
                .inflate(
                    LayoutInflater.from(context)
                ),
            onClickItem
        )

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.assemble(products[position])
    }

    inner class ProductViewHolder(
        private val binding: ProductLayoutBinding,
        private val onClickItem: (product: Product) -> Unit
    ) :RecyclerView.ViewHolder(binding.root) {

        private lateinit var product: Product

        fun assemble(product: Product) {
            this.product = product
            binding.productImage.load(product.image)
            binding.productName.text = product.name
            binding.productPrice.text = product.price.toString()
        }




        init {
            itemView.setOnClickListener {
                if (::product.isInitialized) {
                    onClickItem(product)
                }
            }
        }

    }
}