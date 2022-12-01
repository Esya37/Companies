package com.example.companies.dependencies_injection.modules

import com.example.companies.fragments.CompaniesFragment
import com.example.companies.fragments.CompanyDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun provideCompaniesFragment(): CompaniesFragment

    @ContributesAndroidInjector
    abstract fun provideCompanyDetailsFragment(): CompanyDetailsFragment

}