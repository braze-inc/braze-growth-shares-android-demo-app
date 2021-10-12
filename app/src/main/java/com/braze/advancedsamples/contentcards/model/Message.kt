package com.braze.advancedsamples.contentcards.model

import kotlin.math.floor

open class Message(
metadata: Map<String, Any>,
) : ContentCardable(metadata)  {
    var cardDescription = metadata[Keys.cardDescription] as? String
    var messageHeader = metadata[Keys.messageHeader] as? String
    var messageTitle = metadata[Keys.messageTitle] as? String
    var icon = metadata[image] as? String
    var id = floor(Math.random()*1000).toInt()
    var created = metadata[Keys.created] as? Long
}