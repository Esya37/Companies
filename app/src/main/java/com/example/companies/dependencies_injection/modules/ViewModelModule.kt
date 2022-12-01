package com.example.companies.dependencies_injection.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.companies.dependencies_injection.annotations.ViewModelKey
import com.example.companies.viewmodels.CompanyViewModel
import com.example.companies.viewmodels.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CompanyViewModel::class)
    abstract fun bindMainScreenFragmentViewModel(viewModel: CompanyViewModel): ViewModel
}