package com.example.companies.dependencies_injection.modules

import android.app.Application
import android.content.Context
import com.example.companies.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @ContributesAndroidInjector(modules = [FragmentsModule::class])
    abstract fun providesMainActivity(): MainActivity

}