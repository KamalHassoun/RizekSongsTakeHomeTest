package com.test.rizeksongstest.viewmodels

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.test.rizeksongstest.BR
import com.test.rizeksongstest.models.SpotifyResult
import com.test.rizeksongstest.models.SpotifyResultList
import com.test.rizeksongstest.retrofit.ISpotifyApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SongsViewModel @Inject constructor(
    private val spotifyApi: ISpotifyApi
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val resultObserver = MediatorLiveData<SpotifyResult>()
    private val mObserver = Observer()
    private var lastSearchedString = ""

    fun getObserver() = mObserver
    fun getLastSearchedString() = lastSearchedString

    fun getResultObserver(): LiveData<SpotifyResult> = resultObserver

    fun clearSearch() {
        mObserver.setSearchValue("")
    }

    fun search() {
        disposables.clear()
        lastSearchedString = mObserver.getSearchValue()
        val disposable = spotifyApi.getSongs(mObserver.getSearchValue())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> resultObserver.value = result })
            { resultObserver.value = SpotifyResult(SpotifyResultList(arrayListOf())) }

        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    inner class Observer: BaseObservable() {
        private var mSearch: String = ""

        @Bindable
        fun getSearchValue() = mSearch

        fun setSearchValue(value: String) {
            mSearch = value
            notifyPropertyChanged(BR.searchValue)
        }
    }
}