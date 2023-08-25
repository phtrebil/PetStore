package com.example.petstore.model

import android.os.Parcel
import android.os.Parcelable
import java.lang.StringBuilder

class Product(
    val image: String,
    val name: String,
    val description: String,
    val price: Double,
    val type: Category
) : Parcelable {
    // Converte a enumeração de categoria em uma string descritiva.
    // Código funciona, mas toda vez que modificar o enum temos que modificar essa função também.
    fun CategoryToString(): String {
        val stringBuilder = StringBuilder()

        when (type) {
            Category.CAMAS -> stringBuilder.append("Camas")
            Category.BRINQUEDOS -> stringBuilder.append("Brinquedos")
            Category.COMEDOUROS -> stringBuilder.append("Comedouros")
            Category.CASINHAS -> stringBuilder.append("Casinhas")
        }

        return stringBuilder.toString()
    }

    // Métodos para a implementação Parcelable
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(image)
        dest.writeString(name)
        dest.writeString(description)
        dest.writeDouble(price)
        dest.writeInt(type.ordinal)
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readDouble(),
                Category.values()[parcel.readInt()]
            )
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}

// Enumeração para categorias de produtos.
// Ao adiconar nova categoria lembrar de de atualizar a função categoryToString(), da classe Product.
// E também o init do ProductListAdapter
enum class Category {
    CAMAS, BRINQUEDOS, COMEDOUROS, CASINHAS;
}
