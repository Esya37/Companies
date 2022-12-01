package com.example.companies.repositories

import com.example.companies.api.CompanyApi
import com.example.companies.models.Company
import com.example.companies.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CompanyRepository @Inject constructor(
    val companyApi: CompanyApi
) {
    suspend fun getCompaniesList(): Result<List<Company>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = companyApi.getCompaniesList()
                if (response.isSuccessful) {
                    Result.Success(response.body()!!)
                } else {
                    Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                Result.Error(
                    Exception(e.message!!)
                )
            }
        }
    }

    suspend fun getCompany(id: String): Result<Company> {
        return withContext(Dispatchers.IO) {
            try {
                val response = companyApi.getCompany(id)
                if (response.isSuccessful) {
                    Result.Success(response.body()!![0])
                } else {
                    Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                Result.Error(
                    Exception(e.message!!)
                )
            }
        }
    }
}