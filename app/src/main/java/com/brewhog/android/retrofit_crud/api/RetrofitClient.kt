package com.brewhog.android.retrofit_crud.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    fun getBookInterface() : BookInterface{
        val retrofit = Retrofit.Builder()
            .baseUrl(Utils.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(BookInterface::class.java)
    }
}