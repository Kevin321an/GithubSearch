package com.kevin.githubsearch.data.datasource.remote

import com.kevin.githubsearch.data.model.GitHubRepo
import com.kevin.githubsearch.data.model.GitHubUserInfo
import kotlinx.serialization.Serializable

@Serializable
data class GitHubUserInfoResponse(
        val id: String,
        val avatar_url: String?,
        val name: String?,
        val login:String
)

@Serializable
data class GitHubRepoResponse(
        val id: Int,
        val node_id: String,
        val name: String?,
        val private: Boolean?,
        val forks_count: Int?,
        val description: String?,
        val owner: Owner
)

@Serializable
data class Owner(
        val login:String
)

fun GitHubUserInfoResponse.mapToGitHubUserInfo() = GitHubUserInfo(id, avatar_url?:"", name?:"",login)
fun GitHubRepoResponse.mapToGitHubRepo() = GitHubRepo(id, name?:"", forks_count ?: 0, description?:"", owner.login)