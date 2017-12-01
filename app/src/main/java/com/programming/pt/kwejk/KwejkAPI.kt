package com.programming.pt.kwejk

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface KwejkAPI
{
    @GET("./")
    fun loadFeed(@Query("page") page: Int = 0): Call<KwejkXmlFeed>
}