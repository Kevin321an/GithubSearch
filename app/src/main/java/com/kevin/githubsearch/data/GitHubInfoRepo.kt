package com.kevin.githubsearch.data

import com.kevin.githubsearch.data.model.GitHubRepo
import com.kevin.githubsearch.data.model.GitHubUserInfo
import kotlinx.coroutines.flow.Flow

interface GitHubInfoRepo {
    suspend fun getGitHubRepos(gitHubID: String): Flow<Result<List<GitHubRepo>>>
    suspend fun getGitHubUserInfo(gitHubID: String): Flow<Result<GitHubUserInfo>>
}