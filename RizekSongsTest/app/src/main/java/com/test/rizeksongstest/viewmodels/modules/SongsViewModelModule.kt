package com.test.rizeksongstest.viewmodels.modules

import androidx.lifecycle.ViewModel
import com.test.rizeksongstest.di.scopes.ViewModelKeyScope
import com.test.rizeksongstest.viewmodels.SongsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class SongsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKeyScope(SongsViewModel::class)
    internal abstract fun bindSongsViewModel(songsViewModel: SongsViewModel): ViewModel
}