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
    private val controller by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inicialização do ViewModel com uma fábrica e observação de LiveData.
        viewModel =
            ViewModelProvider(this, HomeViewModelFactory(ProductDB.instance(requireContext()))).get(
                HomeViewModel::class.java
            )
        viewModel.productListLiveData.observe(viewLifecycleOwner) {
            originalProductList = it
            productList = it
            startRecyclerView(productList)
        }

        // Infla o layout do fragmento e retorna a vista resultante.
        binding = FragmentsHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuração do fragmento após a criação da vista.

        // Configuração do botão de retorno personalizado.
        configureReturnButton()

        // Carregamento inicial dos produtos.
        viewModel.loadProducts()

        // Configuração dos botões de filtro.
        configureFilterButton(Category.CAMAS, binding.camas)
        configureFilterButton(Category.BRINQUEDOS, binding.brinquedos)
        configureFilterButton(Category.COMEDOUROS, binding.comedouros)
        configureFilterButton(Category.CASINHAS, binding.casinhas)

        // Configuração da barra de pesquisa.
        configureSearchBar()

        // Configuração do carrinho de compras.
        lifecycleScope.launch { configureShoppingCart() }
    }

    // Função para configurar o comportamento do botão de retorno.
    private fun configureReturnButton() {
        // Configura um callback para lidar com o botão de retorno personalizado.
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (filter) {
                filter = false
                restoreOriginalProductList()
            } else {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }

    // Função para configurar a barra de pesquisa.
    private fun configureSearchBar() {
        // Configura um listener para a barra de pesquisa.
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Filtra os produtos com base na consulta de pesquisa.
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

    // Restaura a lista de produtos original.
    private fun restoreOriginalProductList() {
        productList = originalProductList
        filter = false
        adapter.observeFilterChanges(viewLifecycleOwner) {
            startRecyclerView(productList)
        }
    }

    // Função para configurar os botões de filtro.
    private fun configureFilterButton(category: Category, image: ImageView) {
        image.setOnClickListener {
            filter = true
            val filteredProducts = productList.filter { it.type == category }
            adapter.observeFilterChanges(viewLifecycleOwner) {
                startRecyclerView(filteredProducts)
            }
        }
    }

    // Configuração do carrinho de compras.
    private suspend fun configureShoppingCart() {
        // Verifica se o preço total é zero e oculta o carrinho de compras, se necessário.
        if (viewModel.calculateTotalPrice() == 0.0) {
            binding.carrinho.visibility = View.GONE
        }

        // Configura um diálogo para limpar o carrinho de compras.
        val clearCartDialogHelper = ClearCartDialogHelper(requireContext())

        binding.carrinho.setOnClickListener {
            // Mostra o diálogo para limpar o carrinho de compras.
            clearCartDialogHelper.showClearCartDialog {
                viewModel.clearAllProducts()
                binding.carrinho.visibility = View.GONE
            }
        }
        binding.priceShoppingcart.text = formatPrice(viewModel.calculateTotalPrice())
    }

    // Função para iniciar o RecyclerView com a lista de produtos.
    private fun startRecyclerView(productList: List<Product>) {
        adapter = ProductListAdapter(requireContext(), filter = filter, products = productList)
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.productsRecyclerView.adapter = adapter
        adapter.onClickItem = { product ->
            val bundle = Bundle()
            filter = false
            bundle.putParcelable("product", product)
            controller.navigate(R.id.action_homeFragment_to_detailFragments, bundle)
        }
    }
}

