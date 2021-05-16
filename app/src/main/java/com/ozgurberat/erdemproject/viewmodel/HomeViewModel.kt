package com.ozgurberat.erdemproject.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozgurberat.erdemproject.api.Api
import com.ozgurberat.erdemproject.api.AppClient
import com.ozgurberat.erdemproject.model.GamesResponse
import com.ozgurberat.erdemproject.util.Constants.API_KEY
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










