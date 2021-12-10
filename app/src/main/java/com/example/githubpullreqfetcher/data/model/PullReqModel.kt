package com.example.githubpullreqfetcher.data.model

data class PullResponse(
    val title: String?,
    val created_at: String?,
    val closed_at: String?,
    val user: User?
)

data class User(
    val avatar_url: String?,
    val login: String?
)