package com.example.petstore.ui.fragments

import ProductDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.petstore.R
import com.example.petstore.databinding.FragmentsDetailsBinding
import com.example.petstore.model.Category
import com.example.petstore.model.Product
import com.example.petstore.model.ProductLocal
import com.example.petstore.ui.extensions.formatPrice
import com.example.petstore.ui.viewmodel.DetailViewModel
import com.example.petstore.ui.viewmodel.factory.DetailViewModelFactory

class DetailFragments : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentsDetailsBinding
    private var quantity: Int = 1
    private var product = Product("", "", "", 1.0, Category.CAMAS)
    private val controler by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o ViewModel usando a instância do banco de dados
        viewModel = ViewModelProvider(this, DetailViewModelFactory(ProductDatabase(requireContext()))).get(DetailViewModel::class.java)

        // Obtém o produto da argumento (se existir)
        val args = arguments
        if (args != null && args.containsKey("product")) {
            val getProduct = args.getParcelable<Product>("product")
            getProduct?.let {
                product = it
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentsDetailsBinding.inflate(inflater, container, false)
        val rootView = binding.root

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Carrega os detalhes do produto
        loadProduct(product)

        // Configura a lógica de incremento e decremento da quantidade
        incrementBoxConfiguration()

        // Configura o clique no botão de adicionar ao carrinho
        binding.addButton.setOnClickListener {
            // Cria um objeto ProductLocal e adiciona ao ViewModel
            var productRoom = ProductLocal(0, 0, "","", "", 0.0)
            productRoom.quantity = quantity
            productRoom.productToProductRoom(product)
            viewModel.addProduct(productRoom)

            // Navega de volta para a tela de Home
            controler.navigate(R.id.action_detailFragments_to_homeFragment)
        }
    }

    private fun incrementBoxConfiguration() {
        // Atualiza a quantidade e o preço na interface
        updateQuantityAndPrice()

        // Configura o clique nos botões de incremento e decremento
        binding.incrementButton.setOnClickListener {
            quantity++
            updateQuantityAndPrice()
        }

        binding.decrementButton.setOnClickListener {
            if (quantity > 0) {
                quantity--
                updateQuantityAndPrice()
            }
        }
    }

    private fun updateQuantityAndPrice() {
        // Atualiza a quantidade e o preço na interface
        binding.quantityTextView.text = quantity.toString()
        val totalPrice = product.price * quantity
        val formattedPrice = formatPrice(totalPrice)
        binding.addButton.text = "Adicionar $formattedPrice"
    }

    private fun loadProduct(product: Product) {
        // Carrega os detalhes do produto na interface
        binding.productImage.load(product.image)
        binding.productName.text = product.name
        binding.productDescription.text = product.description
    }
}
