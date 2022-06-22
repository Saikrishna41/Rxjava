package com.example.rxjava2.data.remote

import com.example.rxjava2.Utility.Globals.Companion.BASE_URL
import com.example.rxjava2.data.remote.Service.RequestApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ServiceGenerator {

    companion object {

       private val retrofitBuilder =
            Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())

        private val retrofit = retrofitBuilder.build()

        private val requestApi = retrofit.create(RequestApi::class.java)

        fun getRequestApi() : RequestApi? {
                return requestApi
        }

    }
}