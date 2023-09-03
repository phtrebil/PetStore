package com.example.petstore.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.petstore.databinding.ProductLayoutBinding
import com.example.petstore.model.Product
import com.example.petstore.ui.extensions.formatPrice

class ProducAdapter(
    private val context: Context,
    products: List<Product> = emptyList(),
    var onClickItem: (product: Product) -> Unit = {},
    private val onItemClick: (product: Product) -> Unit
) : RecyclerView.Adapter<ProducAdapter.ProductViewHolder>() {

    private val products = products.toMutableList()

    // Cria uma nova ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ProductLayoutBinding
                .inflate(
                    LayoutInflater.from(context)
                ),
            onClickItem
        )

    // Retorna a quantidade de itens na lista
    override fun getItemCount(): Int = products.size

    // Associa os dados a uma ViewHolder
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.assemble(products[position])
    }

    inner class ProductViewHolder(
        private val binding: ProductLayoutBinding,
        private val onClickItem: (product: Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var product: Product

        // Preenche os dados da ViewHolder com o produto fornecido
        fun assemble(product: Product) {
            this.product = product
            binding.productImage.load(product.image)
            binding.productName.text = product.name
            binding.productPrice.text = formatPrice(product.price)
        }

        init {
            // Configura o clique no item da lista
            itemView.setOnClickListener {
                if (::product.isInitialized) {
                    onItemClick(product)
                }
            }
        }
    }
}
