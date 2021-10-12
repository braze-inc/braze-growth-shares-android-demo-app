package com.braze.advancedsamples.immersive

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appboy.ui.inappmessage.views.AppboyInAppMessageFullView
import com.braze.advancedsamples.R

class CustomImmersiveInAppMessage (private val ctx:Context, attrs: AttributeSet) : AppboyInAppMessageFullView (ctx, attrs) {

    lateinit var listView: RecyclerView
    lateinit var titleView: TextView
    lateinit var subtitleView: TextView

    override fun onFinishInflate() {
        super.onFinishInflate()
//        val imgView = findViewById<ImageView>(R.id.imageView)
//        imgView.setImageURI(Uri.parse("https://dashboard-03.braze.com/dashboard?locale=en"))
        listView = findViewById(R.id.option_list)
        listView.setHasFixedSize(true)
        listView.layoutManager = LinearLayoutManager(ctx)
        listView.adapter = OptionAdapter(ctx, listOf())

        titleView = findViewById(R.id.title)
        subtitleView = findViewById(R.id.subtitle)
    }

    fun setTitle(title:String?){
        titleView.text = title ?: ""
    }

    fun setSubtitle(subtitle:String?){
        subtitleView.text = subtitle ?: ""
    }

    fun setOptions(options: List<String>){
        listView.adapter = OptionAdapter(ctx, options)
    }
}