package com.ozgurberat.erdemproject.api

import com.ozgurberat.erdemproject.model.GameDetail
import com.ozgurberat.erdemproject.model.GamesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("games")
    fun getListOfGames(
        @Query("key") apiKey: String
    ): Observable<GamesResponse>

    @GET("games/{ID}")
    fun getGameDetail(
        @Path("ID") id: String,
        @Query("key") apiKey: String
    ): Observable<GameDetail>


}