package com.example.petstore.data

import android.content.Context
import com.example.petstore.data.local.room.ProductDB
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import org.junit.Test

class ProductDBTest {

    val context = mockk<Context>()

    @Test
    fun `Should not to be null When get instance`() {
        assertNotNull(ProductDB.instance(context))
    }


}
