package com.braze.advancedsamples.contentcards.activity

import android.app.Activity
import android.os.Bundle
import com.braze.advancedsamples.databinding.FullPageContentCardBinding

class FullPageContentCard: Activity() {

    lateinit var binding: FullPageContentCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FullPageContentCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras
        (extras?.get(CONTENT_CARD_IMAGE) as? String)?.let {
            binding.imageView.setImageURI(it)
        }

        (extras?.get(CONTENT_CARD_TITLE) as? String)?.let {
            binding.headline.text = it
        }

        (extras?.get(CONTENT_CARD_DESCRIPTION) as? String)?.let {
            binding.description.text = it
        }


    }

    companion object {
        const val CONTENT_CARD_IMAGE="card_image"
        const val CONTENT_CARD_TITLE="card_title"
        const val CONTENT_CARD_DESCRIPTION="card_description"
    }
}