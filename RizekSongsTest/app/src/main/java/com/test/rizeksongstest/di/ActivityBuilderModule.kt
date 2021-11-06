package com.test.rizeksongstest.di

import com.test.rizeksongstest.ui.MainActivity
import com.test.rizeksongstest.viewmodels.modules.MainViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = [MainViewModelModule::class])
    internal abstract fun contributeMainActivity(): MainActivity
}