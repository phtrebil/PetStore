package com.example.petstore.ui.viewmodel

import com.example.petstore.data.local.room.ProductDB
import com.example.petstore.data.remote.ProductRepository
import com.example.petstore.model.Category
import com.example.petstore.model.Product
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.internal.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.Objects

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    val dao = mockk<ProductDB>()
    private val productRepository = mockk<ProductRepository>()
    private val testDispatcher = TestCoroutineDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Should get room query calculateTotalPrice() function When uses calculateTotalPrice`() =
       runTest {
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
    fun `Should get room query delete() function When uses clearAllProducts()`() = runTest{

        val homeViewModel = HomeViewModel(dao)

        coEvery {
            dao.productDB().delete()
        }.returns(mockk())

        homeViewModel.clearAllProducts()

        coVerify {
            dao.productDB().delete()
        }

    }

    @Test
    fun `Should call getProducts from productService When loadProducts is successful`() = runTest {

        coEvery {
            productRepository.productService.getProducts()
        }.returns(listOf(mockk()))

        val homeViewModel = HomeViewModel(dao)

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

        val homeViewModel = HomeViewModel(dao)

        assertEquals(emptyList(), homeViewModel.loadProducts() as List<Product>)


    }
}


