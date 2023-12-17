package com.kevin.githubsearch.data.datasource.remote

import com.kevin.githubsearch.data.Result
import com.kevin.githubsearch.data.datasource.GitHubDataSource
import com.kevin.githubsearch.data.model.GitHubRepo
import com.kevin.githubsearch.data.model.GitHubUserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GitHubRemoteDataSource(private val gitHubInfoService: GitHubInfoService = provideGitHubInfoService()) :
        GitHubDataSource {

    override suspend fun getGitHubRepos(gitHubID: String): Result<List<GitHubRepo>> {
        try {
            return Result.Success(gitHubInfoService.getReposByUser(gitHubID).map { it.mapToGitHubRepo() })
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun getGitHubUserInfo(gitHubID: String): Result<GitHubUserInfo>  {
        return try {
            Result.Success(gitHubInfoService.getUserInfo(gitHubID).mapToGitHubUserInfo())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}