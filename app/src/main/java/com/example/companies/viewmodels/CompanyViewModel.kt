package com.example.companies.viewmodels

import androidx.lifecycle.ViewModel
import com.example.companies.models.Company
import com.example.companies.repositories.CompanyRepository
import com.example.companies.utils.Result
import javax.inject.Inject

class CompanyViewModel @Inject constructor(
    val companyRepository: CompanyRepository
) : ViewModel() {

    var companiesList: List<Company> = emptyList()
    var currentCompany: Company = Company()
    var selectedCompanyId: String = ""

    suspend fun getCompaniesList(): Result<List<Company>> {
        return companyRepository.getCompaniesList()
    }

    suspend fun getCompany(id: String): Result<Company> {
        return companyRepository.getCompany(id)
    }

}