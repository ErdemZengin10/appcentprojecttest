package com.erdemzengin.VideoGamesAppAppcent.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erdemzengin.VideoGamesAppAppcent.api.Api
import com.erdemzengin.VideoGamesAppAppcent.api.AppClient
import com.erdemzengin.VideoGamesAppAppcent.model.GamesResponse
import com.erdemzengin.VideoGamesAppAppcent.util.Constants.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel: ViewModel() {

    private lateinit var postService: Api

    var listOfGames = MutableLiveData<GamesResponse?>()
    var errorLD = MutableLiveData<Throwable?>()

    @SuppressLint("CheckResult")
    fun getListOfGames() {
        postService = AppClient.getClient().create(Api::class.java)
        postService.getListOfGames(API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ rr ->
                rr?.let {
                    listOfGames.value = rr
                    errorLD.value = null
                } ?: run {
                    listOfGames.value = null
                    errorLD.value = null
                }
            }, { error ->
                errorLD.value = error
                listOfGames.value = null
            })
    }


}










