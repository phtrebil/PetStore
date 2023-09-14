package com.example.petstore.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.petstore.data.local.room.ProductDB
import com.example.petstore.data.remote.ProductRepository
import com.example.petstore.model.Category
import com.example.petstore.model.Product
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    val dao = mockk<ProductDB>()
    private val _productListLiveData = mockk<Observer<List<Product>>>(relaxed = true)
    private val productRepository = mockk<ProductRepository>()
    @ExperimentalCoroutinesApi
    private val testDispatcher  = UnconfinedTestDispatcher()
    private val list = listOf(
    Product(
        image = "imagem",
        name = "Bolinha de roer",
        description = "Bolinha de cachorro para roer",
        price = 19.99,
        type = Category.BRINQUEDOS
    ),
    Product(
        image = "imagem",
        name = "Bolinha de roer",
        description = "Bolinha de cachorro para roer",
        price = 19.99,
        type = Category.CAMAS
    ),
     Product(
        image = "imagem",
        name = "Bolinha de roer",
        description = "Bolinha de cachorro para roer",
        price = 19.99,
        type = Category.COMEDOUROS
    ),
     Product(
        image = "imagem",
        name = "Bolinha de roer",
        description = "Bolinha de cachorro para roer",
        price = 19.99,
        type = Category.CASINHAS
    )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Should get room query calculateTotalPrice() function When uses calculateTotalPrice`() = runTest{
            coEvery {
                dao.productDB().calculateTotalPrice()
            }.returns(10.0)

           val homeViewModel = HomeViewModel(dao)

            val result = homeViewModel.calculateTotalPrice()

            assert(result == 10.0)

            coVerify {
                dao.productDB().calculateTotalPrice()
            }
        }

    @Test
    fun `Should get room query delete() function When uses clearAllProducts()`() {

        val homeViewModel = HomeViewModel(dao)

        coEvery {
            dao.productDB().delete()
        }.returns(mockk())

        homeViewModel.clearAllProducts()

        coVerify {
            dao.productDB().delete()
        }

    }

    fun instanciaViewModel():HomeViewModel{
        val viewModel = HomeViewModel(dao)
        viewModel.productListLiveData.observeForever(_productListLiveData)
        return viewModel
    }


    @Test
    fun `Should call getProducts from productService When loadProducts is successful`() = testDispatcher.run {

        val homeViewModel = instanciaViewModel()

        coEvery {
            productRepository.productService.getProducts()
        } returns list

        homeViewModel.loadProducts()

        coVerify {
           productRepository.productService.getProducts()
        }



    }

    @Test
    fun `Should set an empty list When loadProducts fails`()  = runTest{

        coEvery {
            productRepository.productService.getProducts()
        }.throws(Exception("Erro ao buscar produtos"))

        val homeViewModel = instanciaViewModel()

        homeViewModel.loadProducts()

        homeViewModel.productListLiveData.value?.let { assert(it.isEmpty()) }
    }

}


