package com.erdemzengin.VideoGamesAppAppcent.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erdemzengin.VideoGamesAppAppcent.api.Api
import com.erdemzengin.VideoGamesAppAppcent.api.AppClient
import com.erdemzengin.VideoGamesAppAppcent.local.AppDatabase
import com.erdemzengin.VideoGamesAppAppcent.model.GameDetail
import com.erdemzengin.VideoGamesAppAppcent.util.Constants
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















