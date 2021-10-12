package com.braze.advancedsamples.contentcards.model


class WebViewMessage(
                     metadata: Map<String, Any>,
) : Message(metadata) {
    var contentString: String?
    var webViewType: WebViewType
    init{
        metadata[Keys.messageTitle]?.let { messageTitle = it as String }
        metadata[Keys.messageHeader]?.let { messageHeader = it as String }

        when {
            metadata[urlString] != null -> {
                webViewType = WebViewType.URL
                contentString = metadata[urlString] as? String
            }
            metadata[html] != null -> {
                webViewType = WebViewType.HTML
                contentString = metadata[html] as? String
            }
            metadata[contentBlock] != null -> {
                webViewType = WebViewType.CONTENT_BLOCK
                contentString = metadata[contentBlock] as? String
            }
            else -> {
                webViewType = WebViewType.NONE
                contentString = ""
            }
        }
    }

    enum class WebViewType{
        URL,
        HTML,
        CONTENT_BLOCK,
        NONE
    }
}