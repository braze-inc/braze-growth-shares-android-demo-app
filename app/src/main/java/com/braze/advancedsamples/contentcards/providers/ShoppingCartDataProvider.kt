package com.braze.advancedsamples.contentcards.providers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.braze.advancedsamples.BrazeManager
import com.braze.advancedsamples.R
import com.braze.advancedsamples.SingletonHolder
import com.braze.advancedsamples.contentcards.model.ContentCardable
import com.braze.advancedsamples.contentcards.model.Tile
import com.braze.advancedsamples.contentcards.model.TileList
import com.braze.advancedsamples.databinding.CardCellLayoutBinding
import com.braze.advancedsamples.databinding.ShoppingCartCellBinding
import com.google.gson.Gson
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.text.NumberFormat
import java.util.*

class ShoppingCartDataProvider constructor(private var ctx: Context) : BaseAdapter(){

    private var cart: MutableList<Tile> = ShoppingCartHolder.getInstance().cart

    override fun getCount(): Int {
        return cart.size
    }

    override fun getItem(position: Int): Any {
        return cart[position]
    }

    override fun getItemId(position: Int): Long {
        return cart[position].id.toLong()
    }

    fun getPurchaseTotal(couponValue: Double):String{
        return format.format(cart.sumByDouble { tile -> tile.price ?: 0.0} * (1-couponValue))
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ShoppingCartCellBinding = if (convertView != null) {
            convertView.tag as ShoppingCartCellBinding
        } else {
            ShoppingCartCellBinding.inflate(LayoutInflater.from(ctx), parent, false)

        }

        val tile = cart[position]
        tile.logContentCardImpression()
        binding.icon.setImageURI(tile.image)
        binding.title.text = tile.title
        tile.price?.let {
            binding.price.text = format.format(it)
        } ?: run {
            binding.price.text = "Free"
        }
        binding.root.tag = binding
        return binding.root
    }

    private val format = NumberFormat.getCurrencyInstance(Locale.US)
}