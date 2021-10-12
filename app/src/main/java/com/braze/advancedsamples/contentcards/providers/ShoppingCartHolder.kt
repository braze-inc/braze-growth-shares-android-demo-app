package com.braze.advancedsamples.contentcards.providers

import android.content.Context
import com.braze.advancedsamples.NoParameterSingleton
import com.braze.advancedsamples.SingletonHolder
import com.braze.advancedsamples.contentcards.model.Tile
import java.text.NumberFormat
import java.util.*

class ShoppingCartHolder private constructor() {

    val cart: MutableList<Tile> = mutableListOf()

    fun addToCart(tile: Tile) {
        cart.add(tile)
    }


    companion object : NoParameterSingleton<ShoppingCartHolder>(::ShoppingCartHolder) {

    }
}