package com.braze.advancedsamples.contentcards.model

import com.braze.advancedsamples.BrazeManager


abstract class ContentCardable (){

    var cardData: ContentCardData? = null

    constructor(data:Map<String, Any>):this(){
        cardData = ContentCardData(data[idString] as String,
            ContentCardClass.valueFrom(data[classType] as String),
            data[created] as Long,
            data[dismissable] as Boolean)
    }

    val isContentCard: Boolean
        get() = cardData != null

    fun logContentCardClicked() {
        cardData?.let { BrazeManager.getInstance().logContentCardClicked(it.contentCardId) }
    }

    fun logContentCardDismissed() {
        cardData?.let {BrazeManager.getInstance().logContentCardDismissed(it.contentCardId) }
    }

    fun logContentCardImpression() {
        cardData?.let { BrazeManager.getInstance().logContentCardImpression(it.contentCardId) }
    }

    companion object Keys{
        const val idString = "idString"
        const val created = "created"
        const val classType = "class_type"
        const val dismissable = "dismissable"
        const val extras = "extras"
        const val image = "image"
        const val title = "title"
        const val cardDescription = "cardDescription"
        const val messageHeader = "message_header"
        const val messageTitle = "message_title"
        const val html = "html"
        const val discountPercentage = "discount_percentage"
        const val tags = "tile_tags"
        const val contentBlock = "content_block_id"
        const val detail = "tile_detail"
        const val groupStyle = "group_style"
        const val urlString = "urlString"
    }
}

data class ContentCardData (var contentCardId: String,
                            var contentCardClassType: ContentCardClass,
                            var createdAt: Long,
                            var dismissable: Boolean)

enum class ContentCardClass{
    AD,
    COUPON,
    NONE,
    ITEM_TILE,
    ITEM_GROUP,
    MESSAGE_FULL_PAGE,
    MESSAGE_WEB_VIEW;

    companion object {
        // This value must be synced with the `class_type` value that has been set up in your
        // Braze dashboard or its type will be set to `ContentCardClassType.none.`
        fun valueFrom(str: String?): ContentCardClass {
            return when(str?.toLowerCase()){
                "coupon_code" -> COUPON
                "home_tile" -> ITEM_TILE
                "group" -> ITEM_GROUP
                "message_full_page" -> MESSAGE_FULL_PAGE
                "message_webview" -> MESSAGE_WEB_VIEW
                "ad_banner" -> AD
                else -> NONE
            }
        }
    }

}



