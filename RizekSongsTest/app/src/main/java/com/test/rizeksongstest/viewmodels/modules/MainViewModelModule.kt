package com.test.rizeksongstest.viewmodels.modules

import androidx.lifecycle.ViewModel
import com.test.rizeksongstest.di.scopes.ViewModelKeyScope
import com.test.rizeksongstest.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKeyScope(MainViewModel::class)
    internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}