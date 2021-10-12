package com.braze.advancedsamples.contentcards.model

class Coupon (
    metadata: Map<String, Any>
) : ContentCardable(metadata) {
    var imageUrl = metadata[image] as? String
    var extras = metadata[Keys.extras] as? Map<String, Any>
    var discountPercentageString = extras?.get(Keys.discountPercentage) as? String
    var discountPercentage = discountPercentageString?.toDouble()
    val discountMultiplier: Double
        get() = discountPercentage?.div(100) ?: 1.0

}

