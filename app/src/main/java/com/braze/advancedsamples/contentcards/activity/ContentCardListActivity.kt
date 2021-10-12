package com.braze.advancedsamples.contentcards.activity

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import androidx.fragment.app.FragmentActivity
import com.braze.advancedsamples.BrazeManager
import com.braze.advancedsamples.contentcards.providers.CardableTileDataProvider
import com.braze.advancedsamples.contentcards.providers.ShoppingCartHolder
import com.braze.advancedsamples.databinding.TileListBinding


class ContentCardListActivity: FragmentActivity() {

    private lateinit var dataProvider: CardableTileDataProvider
    lateinit var binding:TileListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TileListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataProvider = CardableTileDataProvider(this)
        val listView = binding.listView
        listView.adapter = dataProvider
        listView.onItemClickListener  = AdapterView.OnItemClickListener { parent, view, position, id ->
            val tile = dataProvider[position]
            ShoppingCartHolder.getInstance().addToCart(tile)
            BrazeManager.getInstance().logCustomEvent("Added item to cart")
            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }
    }

}