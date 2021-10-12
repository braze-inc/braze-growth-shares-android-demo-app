package com.braze.advancedsamples

import android.content.Context
import com.appboy.Appboy
import com.appboy.models.cards.BannerImageCard
import com.appboy.models.cards.CaptionedImageCard
import com.appboy.models.cards.Card
import com.appboy.models.cards.ShortNewsCard
import com.braze.advancedsamples.contentcards.ContentCardableObserver
import com.braze.advancedsamples.contentcards.model.*
import com.braze.advancedsamples.contentcards.model.ContentCardable.Keys.cardDescription
import com.braze.advancedsamples.contentcards.model.ContentCardable.Keys.classType
import com.braze.advancedsamples.contentcards.model.ContentCardable.Keys.created
import com.braze.advancedsamples.contentcards.model.ContentCardable.Keys.dismissable
import com.braze.advancedsamples.contentcards.model.ContentCardable.Keys.extras
import com.braze.advancedsamples.contentcards.model.ContentCardable.Keys.html
import com.braze.advancedsamples.contentcards.model.ContentCardable.Keys.idString
import com.braze.advancedsamples.contentcards.model.ContentCardable.Keys.image
import com.braze.advancedsamples.contentcards.model.ContentCardable.Keys.messageHeader
import com.braze.advancedsamples.contentcards.model.ContentCardable.Keys.messageTitle
import com.braze.advancedsamples.contentcards.model.ContentCardable.Keys.title
import com.braze.advancedsamples.contentcards.model.ContentCardable.Keys.urlString
import com.braze.advancedsamples.inapp.slideup.CustomInAppMessageViewWrapperFactory
import com.braze.events.ContentCardsUpdatedEvent
import com.braze.ui.inappmessage.BrazeInAppMessageManager


class BrazeManager private constructor(private val context: Context) {

    private var registeredForContentCardUpdates: Boolean = false
    private val contentCardableObservers = mutableSetOf<ContentCardableObserver>()
    private var cardList: List<Card> = listOf()

    fun userLogin(username:String){
        Appboy.getInstance(context).changeUser(username)
    }

    fun registerForContentCardUpdates(){
        if (!registeredForContentCardUpdates){
            Appboy.getInstance(context).subscribeToContentCardsUpdates(this::onContentCardsUpdated)
            registeredForContentCardUpdates = true
        }
    }

    fun registerContentCardableObserver(observer: ContentCardableObserver){
        registerForContentCardUpdates()
        contentCardableObservers.add(observer)
        requestContentCardUpdate()
        if (cardList.isNotEmpty()){
            observer.onContentCardsChanged(mapCardsToCardables(cardList))
        }
    }

    fun unregisterContentCardableObserver(observer: ContentCardableObserver){
        contentCardableObservers.remove(observer)
    }

    fun configureCustomInAppMessageViewFactory(){
        BrazeInAppMessageManager.getInstance().setCustomInAppMessageViewWrapperFactory(
            CustomInAppMessageViewWrapperFactory()
        )
    }

    fun requestContentCardUpdate(){
        Appboy.getInstance(context).requestContentCardsRefresh(false)
    }

    private fun mapCardsToCardables(cards: List<Card>):List<ContentCardable>{
        return cards.mapNotNull { card ->
            val metadata = HashMap<String, Any>()
            metadata[idString] = card.id
            metadata[created] = card.created
            metadata[dismissable] = card.isDismissibleByUser

            card.url?.let { metadata[urlString] = it }
            card.extras?.let { metadata[extras] = it }
            card.extras?.let {
                metadata[classType] = it[classType] as String
                it[html]?.let { h -> metadata[html] = h }
                it[messageHeader]?.let { s -> metadata[messageHeader] = s }
                it[messageTitle]?.let { s -> metadata[messageTitle] =  s }
            }
            when (card) {
                is BannerImageCard -> {
                    metadata[image] = card.imageUrl
                }
                is CaptionedImageCard -> {
                    metadata[messageTitle] = card.title
                    metadata[image] = card.imageUrl
                    metadata[cardDescription] = card.description
                }
                is ShortNewsCard -> {
                    metadata[title] = card.title
                    metadata[cardDescription] = card.description
                    metadata[image] = card.imageUrl
                }
            }

            createContentCardable(metadata, ContentCardClass.valueFrom(metadata[classType] as? String))
        }
    }

    private fun onContentCardsUpdated(event: ContentCardsUpdatedEvent){
        this.cardList = event.allCards
        val cardables = mapCardsToCardables(event.allCards)
        contentCardableObservers.forEach {
            it.onContentCardsChanged(cardables)
        }
    }

    private fun createContentCardable(metadata: Map<String, Any>, type: ContentCardClass?): ContentCardable?{
        return when(type){
            ContentCardClass.AD -> Ad(metadata)
            ContentCardClass.MESSAGE_WEB_VIEW -> WebViewMessage(metadata)
            ContentCardClass.MESSAGE_FULL_PAGE -> FullPageMessage(metadata)
            ContentCardClass.ITEM_GROUP -> Group(metadata)
            ContentCardClass.ITEM_TILE -> Tile(metadata)
            ContentCardClass.COUPON -> Coupon(metadata)
            else -> null
        }
    }

    fun logCustomEvent(evt:String){
        Appboy.getInstance(context).logCustomEvent(evt)
    }

    fun logContentCardClicked(idString: String?) {
        getContentCard(idString)?.logClick()
    }

    fun logContentCardImpression(idString: String?) {
        getContentCard(idString)?.logImpression()
    }

    fun logContentCardDismissed(idString: String?) {
        getContentCard(idString)?.setIsDismissed(true)
    }


    private fun getContentCard(idString: String?): Card? {
        return cardList.find { it.id == idString }.takeIf { it != null }
    }

    companion object : SingletonHolder<BrazeManager, Context>(::BrazeManager)

}