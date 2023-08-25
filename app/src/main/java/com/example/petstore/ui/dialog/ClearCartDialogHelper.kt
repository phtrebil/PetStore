package com.example.petstore.ui.dialog

import android.app.AlertDialog
import android.content.Context

class ClearCartDialogHelper(private val context: Context) {

    fun showClearCartDialog(onCleared: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Esvaziar Carrinho")
        builder.setMessage("Tem certeza de que deseja esvaziar o carrinho?")
        builder.setPositiveButton("Sim") { _, _ ->
            onCleared()
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}

