package com.example.petstore.ui.fragments

import ProductDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petstore.R
import com.example.petstore.databinding.FragmentsHomeBinding
import com.example.petstore.model.Category
import com.example.petstore.model.Category.BRINQUEDOS
import com.example.petstore.model.Category.CAMAS
import com.example.petstore.model.Category.CASINHAS
import com.example.petstore.model.Category.COMEDOUROS
import com.example.petstore.model.Product
import com.example.petstore.ui.adapter.ProductListAdapter
import com.example.petstore.ui.dialog.ClearCartDialogHelper
import com.example.petstore.ui.extensions.formatPrice
import com.example.petstore.ui.viewmodel.HomeViewModel
import com.example.petstore.ui.viewmodel.factory.HomeViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentsHomeBinding
    private lateinit var adapter: ProductListAdapter
    private lateinit var productList: List<Product>
    private val controler by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this, HomeViewModelFactory(ProductDatabase(requireContext()))).get(
                HomeViewModel::class.java
            )
        viewModel.productListLiveData.observe(viewLifecycleOwner, Observer {
            productList = it
            startRecyclerView(productList)
        })

        binding = FragmentsHomeBinding.inflate(inflater, container, false)
        val rootView = binding.root

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureShoppingCart()
        viewModel.loadProducts()
        configureButtonFilter(CAMAS, binding.camas)
        configureButtonFilter(BRINQUEDOS, binding.brinquedos)
        configureButtonFilter(COMEDOUROS, binding.comedouros)
        configureButtonFilter(CASINHAS, binding.casinhas)
    }

    private fun configureButtonFilter(category: Category, image: ImageView) {
        image.setOnClickListener {
            val filteredProducts = productList.filter { it.type == category }
            startRecyclerView(filteredProducts)
        }
    }

    private fun configureShoppingCart() {
        if (viewModel.calculateTotalPrice() == 0.0) {
            binding.carrinho.visibility = View.GONE
        }

        val clearCartDialogHelper = ClearCartDialogHelper(requireContext())

        binding.carrinho.setOnClickListener {
            clearCartDialogHelper.showClearCartDialog {
                viewModel.clearAllProducts()
                binding.carrinho.visibility = View.GONE
            }
        }
        binding.priceShoppingcart.text = formatPrice(viewModel.calculateTotalPrice())
    }

    private fun startRecyclerView(productList: List<Product>) {
        adapter = ProductListAdapter(requireContext(), products = productList)
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.productsRecyclerView.adapter = adapter
        adapter.onClickItem = { product ->
            val bundle = Bundle()
            bundle.putParcelable("product", product)
            controler.navigate(R.id.action_homeFragment_to_detailFragments, bundle)
        }
    }
}
