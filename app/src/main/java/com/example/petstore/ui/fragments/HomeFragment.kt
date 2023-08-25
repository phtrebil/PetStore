package com.example.petstore.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petstore.R
import com.example.petstore.data.remote.ProductRepository
import com.example.petstore.databinding.FragmentsHomeBinding
import com.example.petstore.model.Product
import com.example.petstore.ui.adapter.ProductListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentsHomeBinding
    private lateinit var adapter: ProductListAdapter
    private val controler by lazy {
        findNavController()
    }

    private var productList: List<Product> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentsHomeBinding.inflate(inflater, container, false)
        val rootView = binding.root

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                productList = ProductRepository().productService.getProducts()
                adapter = ProductListAdapter(requireContext(), products =  productList)
                startRecyclerView()

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Produtos não encontrados", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        if (productList.isNotEmpty()) {
            startRecyclerView()
        }
    }

    private fun startRecyclerView() {
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.productsRecyclerView.adapter = adapter
        adapter.onClickItem ={ product ->
            val bundle = Bundle()
            bundle.putParcelable("product", product)
            controler.navigate(R.id.action_homeFragment_to_detailFragments, bundle)
        }

    }


}