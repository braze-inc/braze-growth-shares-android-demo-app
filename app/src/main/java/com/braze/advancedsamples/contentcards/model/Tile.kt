package com.braze.advancedsamples.contentcards.model

import com.google.gson.annotations.SerializedName
import kotlin.math.floor

/**
 * This class is the model behind the course list displayed within the application.
 * It supports being instantiated via a Braze ContentCard, or via JSON.
 */
class Tile: ContentCardable {

    @SerializedName("title")
    var title:String? = ""

    @SerializedName("image")
    var image:String? = ""

    @SerializedName("detail")
    var detail:String? = ""

    @SerializedName("tags")
    var tags:List<String>? = listOf()

    @SerializedName("price")
    var price:Double? = 0.0

    @SerializedName("id")
    var id:Int = 0

    constructor():super()

    constructor(metadata:Map<String, Any>):super(metadata){
        val extras = metadata[extras] as? Map<String, Any>
        title = extras?.get(Keys.title) as? String
        image = extras?.get(Keys.image) as? String
        detail = extras?.get(ContentCardable.detail) as? String
        tags = (metadata[ContentCardable.tags] as? String)?.split(",")
        val priceString = extras?.get(Keys.price) as? String
        if (priceString?.isNotEmpty() == true){
            price = priceString.toDouble()
        }
        id = floor(Math.random()*1000).toInt()
    }

    companion object Keys{
        const val price = "tile_price"
        const val image = "tile_image"
        const val title = "tile_title"
    }
}