package com.braze.advancedsamples.immersive

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appboy.Appboy
import com.braze.advancedsamples.R
import com.braze.models.outgoing.BrazeProperties

class OptionAdapter(private val ctx: Context, private val options:List<String>): RecyclerView.Adapter<OptionAdapter.OptionViewHolder>() {

    private val mInflater:LayoutInflater = LayoutInflater.from(ctx)

    fun logClick(value:String, checked:Boolean){
        Appboy.getInstance(ctx).logCustomEvent("SwitchChanged", BrazeProperties())
    }

    inner class OptionViewHolder(item: View): RecyclerView.ViewHolder(item), View.OnClickListener{

        var value: String = ""

        override fun onClick(p0: View?) {
            if (p0 is Switch){
                val checked = p0.isChecked
                p0.isChecked = !p0.isChecked
                logClick(value, checked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        return OptionViewHolder(mInflater.inflate(R.layout.switch_cell, null))
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.label).text = options[position]
        holder.value = options[position]
    }

    override fun getItemCount(): Int {
        return options.size
    }


}