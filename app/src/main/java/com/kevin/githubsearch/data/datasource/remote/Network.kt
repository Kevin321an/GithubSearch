package com.kevin.githubsearch.data.datasource.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

const val GITHUB_BASE_URL = "https://api.github.com/"

interface GitHubInfoService {
    @GET("users/{id}/repos")
    suspend fun getReposByUser(@Path("id") id: String): List<GitHubRepoResponse>

    @GET("users/{id}")
    suspend fun getUserInfo(@Path("id") id: String): GitHubUserInfoResponse
}

fun provideGitHubInfoService(
        isDebug: Boolean = true): GitHubInfoService = createNetworkClient(GITHUB_BASE_URL, isDebug).build()
        .create(GitHubInfoService::class.java)

fun createNetworkClient(baseUrl: String = GITHUB_BASE_URL,
        debug: Boolean = true) = retrofitClient(baseUrl, httpClient(debug))

private fun httpClient(debug: Boolean): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = OkHttpClient.Builder()
    if (debug) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(httpLoggingInterceptor)
    }
    return clientBuilder.build()
}

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(baseUrl).client(httpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))