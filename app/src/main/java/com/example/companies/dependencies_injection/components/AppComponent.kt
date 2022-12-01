package com.example.companies.dependencies_injection.components

import android.app.Application
import com.example.companies.ApplicationClass
import com.example.companies.dependencies_injection.modules.AppModule
import com.example.companies.dependencies_injection.modules.NetworkModule
import com.example.companies.dependencies_injection.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class]
)
interface AppComponent : AndroidInjector<ApplicationClass> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}