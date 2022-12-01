package com.example.companies.api

import com.example.companies.models.Company
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CompanyApi {
    @GET("test.php")
    suspend fun getCompaniesList(): Response<List<Company>>

    @GET("test.php")
    suspend fun getCompany(@Query("id") id:String): Response<List<Company>>
}