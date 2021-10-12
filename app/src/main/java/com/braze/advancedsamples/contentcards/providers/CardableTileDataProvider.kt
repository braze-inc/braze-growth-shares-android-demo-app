package com.braze.advancedsamples.contentcards.providers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.braze.advancedsamples.BrazeManager
import com.braze.advancedsamples.R
import com.braze.advancedsamples.contentcards.model.ContentCardable
import com.braze.advancedsamples.contentcards.ContentCardableObserver
import com.braze.advancedsamples.contentcards.model.Tile
import com.braze.advancedsamples.contentcards.model.TileList
import com.braze.advancedsamples.databinding.CardCellLayoutBinding
import com.google.gson.Gson
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.text.NumberFormat
import java.util.*


class CardableTileDataProvider(private var ctx: Context) : BaseAdapter(),
    ContentCardableObserver {

    private var localTiles: List<Tile> = mutableListOf()
    private var currentTiles: List<Tile> = listOf()

    init {
        BrazeManager.getInstance().registerContentCardableObserver(this)
        localTiles = Gson().fromJson(
            InputStreamReader(ctx.resources.openRawResource(R.raw.tiles)),
            TileList::class.java
        ).tiles
        currentTiles = localTiles
    }

    override fun onContentCardsChanged(cards: List<ContentCardable>) {
        MainScope().launch { updateCards(cards) }
    }

    private fun updateCards(cards: List<ContentCardable>) {
        currentTiles = cards.filterIsInstance<Tile>() + localTiles
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return currentTiles.size
    }

    operator fun get(i: Int) = currentTiles[i]

    override fun getItem(position: Int): Any {
        return currentTiles[position]
    }

    override fun getItemId(position: Int): Long {
        return currentTiles[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: CardCellLayoutBinding = if (convertView != null) {
            convertView.tag as CardCellLayoutBinding
        } else {
            CardCellLayoutBinding.inflate(LayoutInflater.from(ctx), parent, false)

        }


        val tile = currentTiles[position]
        tile.logContentCardImpression()
        binding.cellImage.setImageURI(tile.image)
        binding.cellTitle.text = tile.title
        binding.cellDescription.text = tile.detail
        tile.price?.let {
            binding.price.text = format.format(it)
        } ?: run {
            binding.price.text = "Free"
        }
        binding.root.tag = binding
        return binding.root
    }

    companion object {
        private val format = NumberFormat.getCurrencyInstance(Locale.US)
    }


}