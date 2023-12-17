package com.kevin.githubsearch.data.model

import androidx.compose.runtime.Immutable
@Immutable
data class GitHubUserInfo(val id: String, val image_url: String, val name: String, val login:String)
@Immutable
data class GitHubRepo(val id: Int, val name: String, val forks_count: Int, val description: String, val login:String)