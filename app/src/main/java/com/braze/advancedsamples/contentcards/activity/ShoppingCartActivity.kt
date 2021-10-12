package com.braze.advancedsamples.contentcards.activity

import android.R
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.braze.advancedsamples.BrazeManager
import com.braze.advancedsamples.contentcards.ContentCardableObserver
import com.braze.advancedsamples.contentcards.model.ContentCardable
import com.braze.advancedsamples.contentcards.model.Coupon
import com.braze.advancedsamples.contentcards.providers.ShoppingCartDataProvider
import com.braze.advancedsamples.databinding.ShoppingCartBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class ShoppingCartActivity: FragmentActivity(), ContentCardableObserver {

    private lateinit var binding: ShoppingCartBinding
    private lateinit var dataProvider: ShoppingCartDataProvider
    private var coupons: List<Coupon> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BrazeManager.getInstance(this).registerContentCardableObserver(this)
        dataProvider = ShoppingCartDataProvider(this)
        binding = ShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listView = binding.listView
        listView.adapter = dataProvider
        binding.couponImage.visibility = View.GONE
        binding.purchaseButton.text = "PURCHASE FOR ${dataProvider.getPurchaseTotal(0.0)}"
    }

    override fun onContentCardsChanged(cards: List<ContentCardable>) {
        coupons = cards.filterIsInstance<Coupon>()
        if (coupons.isNotEmpty()){
            MainScope().launch {
                binding.couponImage.setImageURI(coupons[0].imageUrl)
                binding.couponImage.visibility = View.VISIBLE
                binding.couponImage.setOnClickListener {
                    AlertDialog.Builder(this@ShoppingCartActivity)
                        .setTitle("Coupon Added")
                        .setMessage("Your price has been updated") // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Woo!", DialogInterface.OnClickListener { dialog, which ->
                            // Continue with delete operation
                        }) // A null listener allows the button to dismiss the dialog and take no further action.
                        .setIcon(R.drawable.ic_dialog_alert)
                        .show()
                    binding.couponImage.visibility = View.GONE
                    binding.purchaseButton.text = "PURCHASE FOR ${dataProvider.getPurchaseTotal(coupons[0].discountMultiplier)}"
                }
            }
        }
    }
}