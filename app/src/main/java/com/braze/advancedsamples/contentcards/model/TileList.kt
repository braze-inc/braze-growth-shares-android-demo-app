package com.braze.advancedsamples.contentcards.model

import com.google.gson.annotations.SerializedName


data class TileList(@SerializedName("tiles") val tiles:List<Tile>)