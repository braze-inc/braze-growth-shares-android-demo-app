package com.braze.advancedsamples.contentcards.model

/**
 * The object that represents the inline ad banner on the home screen.
 * Can be instantiated from Content Card payload data.
 */
class Ad(
    metadata: Map<String, Any>
) : ContentCardable(metadata) {
    var imageUrl = metadata[image] as? String
}