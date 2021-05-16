package com.ozgurberat.erdemproject.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozgurberat.erdemproject.api.Api
import com.ozgurberat.erdemproject.api.AppClient
import com.ozgurberat.erdemproject.local.AppDatabase
import com.ozgurberat.erdemproject.model.GameDetail
import com.ozgurberat.erdemproject.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailViewModel: ViewModel() {

    private lateinit var postService: Api

    var game = MutableLiveData<GameDetail?>()
    var errorLD = MutableLiveData<Throwable?>()

    @SuppressLint("CheckResult")
    fun getGame(id: String) {
        postService = AppClient.getClient().create(Api::class.java)
        postService.getGameDetail(id, Constants.API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ rr ->
                rr?.let {
                    game.value = rr
                    errorLD.value = null
                } ?: run {
                    game.value = null
                    errorLD.value = null
                }
            }, { error ->
                errorLD.value = error
                game.value = null
            })
    }

    suspend fun putGameIntoDatabase(context: Context, game: GameDetail) {
        val dao = AppDatabase.invoke(context).gameDao()
        dao.insertGame(game)
    }

    suspend fun deleteGameFromDatabase(context: Context, game: GameDetail) {
        val dao = AppDatabase.invoke(context).gameDao()
        dao.deleteGame(game)
    }

}















