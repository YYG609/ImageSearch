package com.android.imagesearch.retrofit

import com.android.imagesearch.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetWorkClient {
    // 서비스 URL
    private const val SERVICE_URL = "https://dapi.kakao.com/"

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        // 통신이 잘 안될 때 디버깅을 위한 용도
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    // 3. searchRetrofit이 create 될 때 URL, Gson컨버팅, 클라이언트 = createOkHttpClient()
    private val searchRetrofit = Retrofit.Builder()
        .baseUrl(SERVICE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient())
        .build()

    // 1. NetWorkInterface 타입의 searchNetWork 변수 선언
    // 2. NetWorkInterface가 파라미터인 searchRetrofit.create()
    val searchNetWork: NetWorkInterface = searchRetrofit.create(NetWorkInterface::class.java)
}