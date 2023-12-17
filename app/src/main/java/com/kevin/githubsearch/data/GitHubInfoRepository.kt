package com.kevin.githubsearch.data

import androidx.compose.material3.rememberModalBottomSheetState
import com.kevin.githubsearch.data.datasource.remote.GitHubRemoteDataSource
import com.kevin.githubsearch.data.datasource.remote.mapToGitHubRepo
import com.kevin.githubsearch.data.model.GitHubRepo
import com.kevin.githubsearch.data.model.GitHubUserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GitHubInfoRepository(private val remoteDataSource: GitHubRemoteDataSource) : GitHubInfoRepo {
    //TODO To create a local data source that uses a database to store information.

    //To minimize the number of API calls, it would be beneficial to store the existing repositories in memory.
    private var repos: List<GitHubRepo> = mutableListOf()

    override suspend fun getGitHubRepos(gitHubID: String): Flow<Result<List<GitHubRepo>>> {
        if (repos.isNotEmpty() && repos[0].login == gitHubID)
            return flow { emit(Result.Success(repos)) }
        val res = remoteDataSource.getGitHubRepos(gitHubID = gitHubID)
        if (res is Result.Success) repos = res.data
        return flow { emit(res) }
    }

    override suspend fun getGitHubUserInfo(gitHubID: String): Flow<Result<GitHubUserInfo>> {
        return flow { emit(remoteDataSource.getGitHubUserInfo(gitHubID = gitHubID)) }
    }

    fun getCurrentRepos() = repos
}