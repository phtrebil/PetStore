package com.example.petstore.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.petstore.R
import com.example.petstore.databinding.FragmentsDetailsBinding
import com.example.petstore.databinding.FragmentsHomeBinding
import com.example.petstore.model.Category
import com.example.petstore.model.Product

class DetailFragments: Fragment() {

    private lateinit var binding: FragmentsDetailsBinding

    private var product = Product("", "", "", 1.0, Category.CAMAS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        loadProduct(product)
    }

    private fun loadProduct(product: Product) {
        binding.productImage.load(product.image)
        binding.productName.text = product.name
        binding.productDescription.text = product.description
    }
}