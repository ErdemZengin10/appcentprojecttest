package com.ozgurberat.erdemproject.api

import com.ozgurberat.erdemproject.util.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AppClient {

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {

        retrofit?.let {
            return it
        }?: run{
            retrofit =
                Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit!!
        }

    }
}