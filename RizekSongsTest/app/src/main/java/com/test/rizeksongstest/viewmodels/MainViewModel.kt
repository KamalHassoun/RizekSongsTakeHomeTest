package com.test.rizeksongstest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.test.rizeksongstest.helpers.DataRequestResource
import com.test.rizeksongstest.helpers.SpotifyApiInterceptor
import com.test.rizeksongstest.models.AccessToken
import com.test.rizeksongstest.retrofit.ITokenApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val tokenApi: ITokenApi,
    private val spotifyApiInterceptor: SpotifyApiInterceptor
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val tokenObserver = MediatorLiveData<DataRequestResource<AccessToken>>()

    @Inject
    fun onStart() {
        requestToken()
    }

    fun getTokenObserver(): LiveData<DataRequestResource<AccessToken>> = tokenObserver

    private fun requestToken() {
        disposables.clear()
        tokenObserver.value = DataRequestResource.Loading()
        val disposable = tokenApi.fetchToken()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                spotifyApiInterceptor.setToken(result)
                tokenObserver.value = DataRequestResource.Success(result)
            })
            { t -> tokenObserver.value = DataRequestResource.Error(t, null) }

        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}