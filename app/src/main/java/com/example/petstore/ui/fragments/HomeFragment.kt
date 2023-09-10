package com.example.petstore.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petstore.R
import com.example.petstore.data.local.room.ProductDB
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
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentsHomeBinding
    private lateinit var adapter: ProductListAdapter
    private lateinit var originalProductList: List<Product>
    private lateinit var productList: List<Product>
    private var filter: Boolean = false
    private val controler by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this, HomeViewModelFactory(ProductDB.instance(requireContext()))).get(
                HomeViewModel::class.java
            )
        viewModel.productListLiveData.observe(viewLifecycleOwner) {
            originalProductList = it
            productList = it
            startRecyclerView(productList)
        }

        binding = FragmentsHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {configureShoppingCart()  }
        viewModel.loadProducts()
        configureButtonFilter(CAMAS, binding.camas)
        configureButtonFilter(BRINQUEDOS, binding.brinquedos)
        configureButtonFilter(COMEDOUROS, binding.comedouros)
        configureButtonFilter(CASINHAS, binding.casinhas)
        configureSearchBar()
        configureReturnBotton()
    }

    private fun configureReturnBotton() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (filter) {
                filter = false
                restoreOriginalProductList()
            } else {
                // Caso contrário, permita o comportamento padrão de voltar.
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }

    private fun configureSearchBar() {
        binding.searchView.setOnQueryTextListener(object: OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val filteredProducts = productList.filter {
                    it.name.contains(query.orEmpty(), ignoreCase = true)
                }
                filter = true
                adapter.observeFilterChanges(viewLifecycleOwner) {

                    startRecyclerView(filteredProducts)
                }

                return true

            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
    }

    private fun restoreOriginalProductList() {
        productList = originalProductList
        filter = false
        adapter.observeFilterChanges(viewLifecycleOwner) {
            startRecyclerView(productList)
        }
    }


    private fun configureButtonFilter(category: Category, image: ImageView) {
        image.setOnClickListener {
            filter = true
            val filteredProducts = productList.filter { it.type == category }
            adapter.observeFilterChanges(viewLifecycleOwner) {
                startRecyclerView(filteredProducts)
            }
        }
    }

    private suspend fun configureShoppingCart() {
        if (viewModel.calculateTotalPrice()== 0.0) {
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
        adapter = ProductListAdapter(requireContext(), filter = filter, products = productList)
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.productsRecyclerView.adapter = adapter
        adapter.onClickItem = { product ->
            val bundle = Bundle()
            filter = false
            bundle.putParcelable("product", product)
            controler.navigate(R.id.action_homeFragment_to_detailFragments, bundle)
        }
    }

}
