package com.android.imagesearch.retrofit

import com.android.imagesearch.BuildConfig
import com.android.imagesearch.data.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface NetWorkInterface {
    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }

    @Headers(API_KEY)
    @GET("v2/search/image")
    // HashMap 형태로 키-밸류 요청
    suspend fun getSearch(@QueryMap param: HashMap<String, String>): SearchResponse
}