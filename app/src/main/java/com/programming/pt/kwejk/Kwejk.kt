package com.programming.pt.kwejk

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class Kwejk
{
    var isInitialized = false
    val images = ImageList(-1, ArrayList(), this::loadFeed)

    private val url = "https://api.kwejk.pl"
    private val api: KwejkAPI
    init {
        val retrofit = Retrofit.Builder().baseUrl(url).addConverterFactory(SimpleXmlConverterFactory.create()).build()
        api = retrofit.create(KwejkAPI::class.java)
    }

    fun start(onSuccessCallback: (() -> Unit)?, onFailureCallback: (() -> Unit)?) {
        loadFeed(0, onSuccessCallback, onFailureCallback)
    }

    private fun loadFeed(page: Int, onSuccessCallback: (() -> Unit)?, onFailureCallback: (() -> Unit)?) {
        val feed = api.loadFeed(page)
        feed.enqueue(object : Callback<KwejkXmlFeed> {
            override fun onFailure(call: Call<KwejkXmlFeed>?, t: Throwable?) {
                t?.printStackTrace()
                onFailureCallback?.invoke()
            }
            override fun onResponse(call: Call<KwejkXmlFeed>?, response: Response<KwejkXmlFeed>?) {
                response?.let {
                    if (response.isSuccessful) {
                        isInitialized = true
                        val result = response.body()
                        images.currentPage = result.currentPage
                        images.source.addAll(result.images.filter { it.isNotArticle() })
                        onSuccessCallback?.invoke()
                    }
                }
            }
        })
    }
}