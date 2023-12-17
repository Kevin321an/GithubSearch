package com.kevin.githubsearch.data.datasource

import com.kevin.githubsearch.data.model.GitHubRepo
import com.kevin.githubsearch.data.model.GitHubUserInfo
import kotlinx.coroutines.flow.Flow
import com.kevin.githubsearch.data.Result

interface GitHubDataSource {
    suspend fun getGitHubRepos(gitHubID: String): Result<List<GitHubRepo>>
    suspend fun getGitHubUserInfo(gitHubID: String): Result<GitHubUserInfo>
}