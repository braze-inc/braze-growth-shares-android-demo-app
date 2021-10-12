package com.braze.advancedsamples.contentcards.model

import com.google.gson.annotations.SerializedName

class Group: ContentCardable {

    @SerializedName("id")
    var id: Int? = 0
    @SerializedName("title")
    var title: String? = ""
    @SerializedName("image_url")
    var imageUrl: String? = ""
    @SerializedName("click_url")
    var clickUrl: String? = ""

    constructor():super()

    constructor(metadata: Map<String, Any>) : super(metadata){

    }


}