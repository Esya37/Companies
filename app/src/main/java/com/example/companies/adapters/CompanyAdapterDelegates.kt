package com.example.companies.adapters

import android.widget.ImageView
import android.widget.TextView
import com.example.companies.BuildConfig
import com.example.companies.R
import com.example.companies.models.Company
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.squareup.picasso.Picasso

fun companyAdapterDelegate(clickListener: (Company)->Unit) = adapterDelegate<Company, Company>(R.layout.item_companies) {

    val companyNameTextView: TextView = findViewById(R.id.tv_company_item_name)
    val companyImgImageView: ImageView = findViewById(R.id.iv_company_item_image)

    itemView.setOnClickListener{ clickListener(item) }

    bind {
        companyNameTextView.text = item.name
        Picasso.get()
            .load("${BuildConfig.BASE_URL}/${item.imgUrl}")
            .error(R.drawable.icon_internet_error)
            .into(companyImgImageView)
    }

}