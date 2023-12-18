package com.kevin.githubsearch.data

import androidx.annotation.VisibleForTesting
import com.kevin.githubsearch.data.model.GitHubRepo
import com.kevin.githubsearch.data.model.GitHubUserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.lang.Exception

const val EXCEPTION_MSG = "Test exception"

class FakeGitHubInfoRepository() : GitHubInfoRepo {

    private var repos: List<GitHubRepo> = mutableListOf()
    private val fakeGitHubInfoRepository = MutableStateFlow(mutableListOf<GitHubRepo>())
    private val fakeGitHubUserInfo: MutableStateFlow<GitHubUserInfo?> = MutableStateFlow(null)

    private var isError = false
    private val observablesRepos: Flow<Result<List<GitHubRepo>>> =
            fakeGitHubInfoRepository.map {
                if (isError) {
                    Result.Error(Exception(EXCEPTION_MSG))
                } else {
                    Result.Success(it)
                }
            }

    private val observablesUserInfo: Flow<Result<GitHubUserInfo>> =
            fakeGitHubUserInfo.map {
                if (it == null) {
                    Result.Error(Exception(EXCEPTION_MSG))
                } else {
                    Result.Success(it)
                }
            }

    override suspend fun getGitHubRepos(gitHubID: String): Flow<Result<List<GitHubRepo>>> = observablesRepos

    override suspend fun getGitHubUserInfo(gitHubID: String): Flow<Result<GitHubUserInfo>> = observablesUserInfo

    @VisibleForTesting
    fun addGitHubUserInfo(gitHubUserInfo: GitHubUserInfo) {
        fakeGitHubUserInfo.update { gitHubUserInfo.copy() }
    }

    @VisibleForTesting
    fun addRepos(gitRepos: List<GitHubRepo>) {
        fakeGitHubInfoRepository.update {
            gitRepos.toMutableList()
        }
        repos = if (!isError) {
            gitRepos
        } else emptyList()
    }

    @VisibleForTesting
    fun setError(boolean: Boolean) {
        isError = boolean
    }

    override fun getCurrentRepos(): List<GitHubRepo> {
        return repos
    }
}