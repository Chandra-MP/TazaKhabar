package com.example.tazakhabar.APIsInterface

import com.example.tazakhabar.modelClasses.TotalArticles
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetNewsApi {

    @GET("top-headlines")
    suspend fun get_General_News(
        @Query("country") country: String,
        @Query("pageSize") pageSize: Int,
        @Query("apikey") apiKey: String,
    ): Response<TotalArticles>

    @GET("top-headlines")
   suspend fun get_Category_Wise(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int,
        @Query("apikey") apiKey: String,
    ): Response<TotalArticles>

    @GET("search")
    suspend fun get_News_with_keywords(
        @Query("q") q: String,
        @Query("apikey") apiKey: String,
    ): Response<TotalArticles>
}