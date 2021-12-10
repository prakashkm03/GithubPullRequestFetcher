package com.example.githubpullreqfetcher.data.api

import com.example.githubpullreqfetcher.data.model.PullResponse
import retrofit2.http.GET

interface ApiService {

    @GET("pulls?state=closed")
    suspend fun getUsers(): List<PullResponse>

}