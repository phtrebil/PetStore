package com.example.petstore.ui.fragments

import ProductDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petstore.R
import com.example.petstore.databinding.FragmentsHomeBinding
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
    private val controler by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inicializa o ViewModel com a instância do banco de dados
        viewModel = ViewModelProvider(this, HomeViewModelFactory(ProductDatabase(requireContext()))).get(HomeViewModel::class.java)

        // Observa a lista de produtos do LiveData e inicia o RecyclerView
        viewModel.productListLiveData.observe(viewLifecycleOwner, Observer { productList ->
            startRecyclerView(productList)
        })

        // Infla o layout do fragmento
        binding = FragmentsHomeBinding.inflate(inflater, container, false)
        val rootView = binding.root

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureShoppingCart()

        // Carrega a lista de produtos do servidor
        viewModel.loadProducts()
    }

    private fun configureShoppingCart() {
        // Verifica se o carrinho está vazio e ajusta a visibilidade
        if (viewModel.calculateTotalPrice() == 0.0) {
            binding.carrinho.visibility = View.GONE
        }

        // Cria uma instância do ClearCartDialogHelper para gerenciar o diálogo de confirmação
        val clearCartDialogHelper = ClearCartDialogHelper(requireContext())

        // Configura o clique no botão do carrinho para mostrar o diálogo de confirmação e limpar o carrinho
        binding.carrinho.setOnClickListener {
            clearCartDialogHelper.showClearCartDialog {
                viewModel.clearAllProducts()
                binding.carrinho.visibility = View.GONE
            }
        }
        // Atualiza o preço do carrinho na interface do usuário
        binding.priceShoppingcart.text = formatPrice(viewModel.calculateTotalPrice())
    }

    private fun startRecyclerView(productList: List<Product>) {
        // Inicializa o RecyclerView com a lista de produtos
        adapter = ProductListAdapter(requireContext(), products = productList)
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.productsRecyclerView.adapter = adapter

        // Configura o clique nos itens do RecyclerView para navegar para a tela de detalhes
        adapter.onClickItem ={ product ->
            val bundle = Bundle()
            bundle.putParcelable("product", product)
            controler.navigate(R.id.action_homeFragment_to_detailFragments, bundle)
        }
    }
}
