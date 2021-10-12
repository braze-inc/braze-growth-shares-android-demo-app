package com.braze.advancedsamples.contentcards.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Base64
import android.webkit.WebView
import android.webkit.WebViewClient


class WebViewActivity: Activity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val payload = intent.extras?.get(INTENT_PAYLOAD)
        (payload as? String)?.let {
            val webview = WebView(this)
            webview.settings.javaScriptEnabled = true
            if(it.startsWith("http")){
                webview.loadUrl(it)
            }else{
                webview.loadData(Base64.encodeToString(it.toByteArray(), Base64.NO_PADDING), "text/html", "base64")
            }
            setContentView(webview)
        }
    }


    companion object {
        const val INTENT_PAYLOAD = "PAYLOAD"
    }

}