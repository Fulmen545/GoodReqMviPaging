package com.riso.goodreqmvi.data.api

import com.riso.goodreqmvi.data.model.Page
import com.riso.goodreqmvi.data.model.Profile
import com.riso.goodreqmvi.data.model.User
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import io.reactivex.schedulers.Schedulers
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("users")
    fun getUsers(@Query("page") page: Int, @Query("per_page") perPage: Int): Single<Page>

    @GET("users/{id}")
    fun getDetail(@Path("id") id: Int): Single<Profile>

    companion object {

        private const val BASE_URL = "https://reqres.in/api/"

        private fun getRetrofit() = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


        val apiService: ApiService = getRetrofit().create(ApiService::class.java)

    }

}