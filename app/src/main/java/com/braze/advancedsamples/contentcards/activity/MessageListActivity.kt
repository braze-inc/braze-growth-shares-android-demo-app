package com.braze.advancedsamples.contentcards.activity

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import androidx.fragment.app.FragmentActivity
import com.braze.advancedsamples.contentcards.model.FullPageMessage
import com.braze.advancedsamples.contentcards.model.WebViewMessage
import com.braze.advancedsamples.contentcards.providers.CardableMessageDataProvider
import com.braze.advancedsamples.databinding.MessageListBinding


class MessageListActivity : FragmentActivity() {

    private lateinit var binding: MessageListBinding
    private lateinit var dataProvider: CardableMessageDataProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MessageListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataProvider = CardableMessageDataProvider(this)
        val listView = binding.listView
        listView.adapter = dataProvider
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
           when (val card = dataProvider[position]){
                is WebViewMessage -> {
                    val intent = Intent(this, WebViewActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString(WebViewActivity.INTENT_PAYLOAD, card.contentString)
                    intent.putExtras(bundle)
                    startActivity(intent)
                    card.logContentCardClicked()
                }
                is FullPageMessage -> {
                    val intent = Intent(this, FullPageContentCard::class.java)
                    val bundle = Bundle()
                    bundle.putString(FullPageContentCard.CONTENT_CARD_IMAGE, card.icon)
                    bundle.putString(FullPageContentCard.CONTENT_CARD_TITLE, card.messageTitle)
                    bundle.putString(FullPageContentCard.CONTENT_CARD_DESCRIPTION, card.cardDescription)
                    intent.putExtras(bundle)
                    startActivity(intent)
                    card.logContentCardClicked()
                }
            }

        }
    }


}