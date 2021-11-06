package com.test.rizeksongstest.viewmodels.modules

import androidx.lifecycle.ViewModel
import com.test.rizeksongstest.di.scopes.ViewModelKeyScope
import com.test.rizeksongstest.viewmodels.ViewSongViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewSongsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKeyScope(ViewSongViewModel::class)
    internal abstract fun bindViewSongsViewModel(viewSongViewModel: ViewSongViewModel): ViewModel
}