package com.example.githubpullreqfetcher.data.repository

import com.example.githubpullreqfetcher.data.api.ApiHelper

class PullReqRepo(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

}