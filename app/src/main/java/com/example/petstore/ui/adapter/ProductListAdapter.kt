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
    var onClickItem: (product: Product) -> Unit = {},
    products: List<Product> = emptyList()
) : RecyclerView.Adapter<ProductListAdapter.ProductListAdapterViewHolder>() {

    private val productList: MutableList<Product> = products.toMutableList()
    private val dividedProductLists: MutableList<List<Product>> = mutableListOf()

    /* Divide a lista de produtos em categorias no construtor. Embora tenha achado divertido montar essa lista,
    acredito que seja pouco prático. Tendo em vista que toda vez que adiconar uma nova
    categoria também será necessário modficiar esse código.
     */
    init {
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

    // Cria uma nova ViewHolder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductListLayoutBinding.inflate(inflater, parent, false)
        return ProductListAdapterViewHolder(binding)
    }

    // Retorna a quantidade de categorias
    override fun getItemCount(): Int {
        return dividedProductLists.size
    }

    // Associa os dados de uma categoria a uma ViewHolder
    override fun onBindViewHolder(holder: ProductListAdapterViewHolder, position: Int) {
        holder.assemble(dividedProductLists[position])
    }

    inner class ProductListAdapterViewHolder(val binding: ProductListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Monta a lista de produtos de uma categoria na ViewHolder
        fun assemble(list: List<Product>) {
            val productAdapter = ProducAdapter(context, onClickItem, list) { product ->
                // Aqui, dentro do escopo do ProducAdapter, você usa o onClickItem externo
                onClickItem(product)
            }

            binding.productType.text = list.get(0).CategoryToString()
            binding.productList.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = productAdapter
            }
        }
    }
}
