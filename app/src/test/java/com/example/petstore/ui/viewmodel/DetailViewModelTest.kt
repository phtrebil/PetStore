package com.example.petstore.ui.viewmodel

import com.example.petstore.data.local.room.ProductDB
import com.example.petstore.model.ProductLocal
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


class DetailViewModelTest{

    val product = ProductLocal(1, 3, "imagens", "ossinho", "ossinho de brinquedo", 9.99)
    val dao = mockk<ProductDB>()

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
    fun `Should get room insert function When uses addProduct()`(){

        val detailViewModel = DetailViewModel(dao)

        coEvery {
            dao.productDB().insert(product)
        }.returns(mockk())

        detailViewModel.addProduct(product)

        coVerify {
            dao.productDB().insert(product)
        }

    }
}