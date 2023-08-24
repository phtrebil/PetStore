package com.example.petstore.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petstore.databinding.ProductListLayoutBinding
import com.example.petstore.model.Category
import com.example.petstore.model.Product

class ProductListAdapter(
    private val context: Context,
    private val onClickItem: (product: Product) -> Unit = {},
    products: List<Product> = emptyList()
) : RecyclerView.Adapter<ProductListAdapter.ProductListAdapterViewHolder>() {

    private val productList: MutableList<Product> = products.toMutableList()
    private val dividedProductLists: MutableList<List<Product>> = mutableListOf()

    init {
        // Dividir a lista de produtos em quatro partes (categorias)
        val camas = mutableListOf<Product>()
        val brinquedos = mutableListOf<Product>()
        val comedouros = mutableListOf<Product>()
        val casinhas = mutableListOf<Product>()

        for (product in productList) {
            when (product.type) {
                Category.CAMAS -> camas.add(product)
                Category.BRINQUEDOS -> brinquedos.add(product)
                Category.COMEDOUROS -> comedouros.add(product)
                Category.CASINHAS -> casinhas.add(product)
            }
        }

        dividedProductLists.add(camas)
        dividedProductLists.add(brinquedos)
        dividedProductLists.add(comedouros)
        dividedProductLists.add(casinhas)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductListLayoutBinding.inflate(inflater, parent, false)
        return ProductListAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dividedProductLists.size
    }

    override fun onBindViewHolder(holder: ProductListAdapterViewHolder, position: Int) {
        holder.assemble(dividedProductLists[position])

    }

    inner class ProductListAdapterViewHolder(val binding: ProductListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun assemble(list: List<Product>) {
            val productAdapter = ProducAdapter(context, onClickItem, list)

            binding.productType.text = list.get(0).CategoryToString()
            binding.productList.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = productAdapter
            }
        }
    }
}
