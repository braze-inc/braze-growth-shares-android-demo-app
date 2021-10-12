package com.braze.advancedsamples.contentcards.providers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.braze.advancedsamples.BrazeManager
import com.braze.advancedsamples.contentcards.*
import com.braze.advancedsamples.contentcards.model.ContentCardable
import com.braze.advancedsamples.contentcards.model.Message
import com.braze.advancedsamples.databinding.MessageCenterCellBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class CardableMessageDataProvider(private var ctx: Context) : BaseAdapter(),
    ContentCardableObserver {

    private var currentMessages: List<Message> = listOf()

    init {
        BrazeManager.getInstance().registerContentCardableObserver(this)
    }

    override fun onContentCardsChanged(cards: List<ContentCardable>) {
        MainScope().launch { updateCards(cards) }
    }

    private fun updateCards(cards: List<ContentCardable>) {
        currentMessages = cards.filterIsInstance<Message>()
        notifyDataSetChanged()
    }

    operator fun get(i: Int) = currentMessages[i]

    override fun getCount(): Int {
        return currentMessages.size
    }

    override fun getItem(position: Int): Any {
        return currentMessages[position]
    }

    override fun getItemId(position: Int): Long {
        return currentMessages[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: MessageCenterCellBinding = if (convertView != null) {
            convertView.tag as MessageCenterCellBinding
        } else {
            MessageCenterCellBinding.inflate(LayoutInflater.from(ctx), parent, false)
        }

        val message = currentMessages[position]
        binding.icon.setImageURI(message.icon)
        binding.title.text = message.messageTitle
        message.created?.let {
            binding.date.text = SimpleDateFormat("MM/dd/yyyy", Locale.US).format(Date(it*1000))
        }
        binding.root.tag = binding
        return binding.root
    }

}