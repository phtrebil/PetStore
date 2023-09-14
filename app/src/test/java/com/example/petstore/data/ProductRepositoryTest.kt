package com.example.petstore.data

import com.example.petstore.data.remote.ProductRepository
import com.example.petstore.data.remote.ProductService
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ProductRepositoryTest {

    @Test
    fun `Should not to be null When get instance`() {
        assertNotNull(ProductRepository().productService)
    }


}


