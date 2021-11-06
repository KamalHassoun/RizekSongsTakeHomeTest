package com.test.rizeksongstest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.test.rizeksongstest.models.*
import com.test.rizeksongstest.retrofit.ISpotifyApi
import com.test.rizeksongstest.viewmodels.SongsViewModel
import io.reactivex.Observable
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.*

@RunWith(JUnit4::class)
class SongsUnitTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val schedulersOverrideRule = RxSchedulersOverrideRule()

    @Mock
    private lateinit var spotifyApi: ISpotifyApi
    @Mock
    private lateinit var observer: Observer<SpotifyResult>
    private lateinit var songsViewModel: SongsViewModel

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<SpotifyResult>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        songsViewModel = SongsViewModel(spotifyApi)
        songsViewModel.getResultObserver().observeForever(observer)
    }

    @Test
    fun testHasObservers() {
        assertTrue(songsViewModel.getResultObserver().hasObservers())
    }

    @Test
    fun testApiRequestFailed() {
        val failString = "dggggdddd"
        Mockito.`when`(spotifyApi.getSongs(failString)).thenReturn(
            Observable.just(SpotifyResult(SpotifyResultList(arrayListOf())))
        )
        songsViewModel.getObserver().setSearchValue(failString)
        songsViewModel.search()
        Mockito.verify(
            observer,
            Mockito.times(1)
        ).onChanged(argumentCaptor.capture())
        val values = argumentCaptor.allValues
        assert(values[0].result.tracks.isEmpty())
    }

    @Test
    fun testApiReturnThreeResults() {
        val searchString = "Mabel"
        val mockList = arrayListOf<Track>()
        mockList.add(Track("mabel", "test", Album(arrayListOf()), arrayListOf()))
        mockList.add(Track("eminem", "test", Album(arrayListOf()), arrayListOf()))
        mockList.add(Track("Rihanna", "test", Album(arrayListOf()), arrayListOf()))
        Mockito.`when`(spotifyApi.getSongs(searchString)).thenReturn(
            Observable.just(SpotifyResult(SpotifyResultList(mockList)))
        )
        songsViewModel.getObserver().setSearchValue(searchString)
        songsViewModel.search()
        Mockito.verify(
            observer,
            Mockito.times(1)
        ).onChanged(argumentCaptor.capture())
        val values = argumentCaptor.allValues
        assert(values[0].result.tracks.size == 3)
    }

    @After
    fun cleanUp() {
        Mockito.reset(observer)
    }
}