package com.braze.advancedsamples.contentcards

import com.braze.advancedsamples.contentcards.model.ContentCardable

interface ContentCardableObserver {
    fun onContentCardsChanged(cards: List<ContentCardable>)
}