package com.test.rizeksongstest.di

import com.test.rizeksongstest.ui.fragments.SongsFragment
import com.test.rizeksongstest.ui.fragments.ViewSongFragment
import com.test.rizeksongstest.viewmodels.modules.SongsViewModelModule
import com.test.rizeksongstest.viewmodels.modules.ViewSongsViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector(modules = [SongsViewModelModule::class])
    internal abstract fun contributeSongsFragment(): SongsFragment

    @ContributesAndroidInjector(modules = [ViewSongsViewModelModule::class])
    internal abstract fun contributeViewSongsFragment(): ViewSongFragment
}