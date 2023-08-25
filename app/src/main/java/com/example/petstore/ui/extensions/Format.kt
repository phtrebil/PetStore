package com.example.petstore.ui.extensions

import java.text.DecimalFormat

fun formatPrice(price: Double): String {
    val decimalFormat = DecimalFormat("#.00")
    return "R$" + decimalFormat.format(price)
}
