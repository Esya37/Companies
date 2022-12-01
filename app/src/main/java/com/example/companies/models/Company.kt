package com.example.companies.models

import com.google.gson.annotations.SerializedName

data class Company(
    val id: String = "",
    val name: String = "",
    @SerializedName("img")
    val imgUrl: String = "",
    val description: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    @SerializedName("www")
    val siteAddress: String = "",
    val phone: String = ""
)