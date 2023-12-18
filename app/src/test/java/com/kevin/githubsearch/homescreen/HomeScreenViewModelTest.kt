@file:OptIn(ExperimentalCoroutinesApi::class)

package com.kevin.githubsearch.homescreen

import MainDispatcherRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.kevin.githubsearch.data.FakeGitHubInfoRepository
import com.kevin.githubsearch.data.model.GitHubRepo
import com.kevin.githubsearch.data.model.GitHubUserInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenViewModelTest {
    private lateinit var homeScreenViewModel: HomeScreenViewModel
    private lateinit var repository: FakeGitHubInfoRepository

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        repository = FakeGitHubInfoRepository()
        homeScreenViewModel = HomeScreenViewModel(repository, SavedStateHandle())
    }

    @Test
    fun sateIsNotReady_withEmptySearchQuery_success() = runTest {
        repository.addGitHubUserInfo(userInfo)
        repository.addRepos(repos.toList())

        val collectJob = launch(UnconfinedTestDispatcher()) {
            homeScreenViewModel.searchResultUIState.collect()
        }
        homeScreenViewModel.searchTrigger("")
        Assert.assertEquals(homeScreenViewModel.searchResultUIState.value, SearchResultUiState.NotReady)
        collectJob.cancel()
    }

    @Test
    fun sateIsNotReady_withEmptySearchQuery_fail() = runTest {
        repository.addGitHubUserInfo(userInfo)
        repository.addRepos(repos.toList())

        val collectJob = launch(UnconfinedTestDispatcher()) {
            homeScreenViewModel.searchResultUIState.collect()
        }
        homeScreenViewModel.searchTrigger("")
        Assert.assertNotEquals(homeScreenViewModel.searchResultUIState.value, SearchResultUiState.Success())
        collectJob.cancel()
    }

    @Test
    fun sateIsSuccess_withEmptySearchQuery_success() = runTest {
        repository.addGitHubUserInfo(userInfo)
        repository.addRepos(repos.toList())

        val collectJob = launch(UnconfinedTestDispatcher()) {
            homeScreenViewModel.searchResultUIState.collect()
        }
        homeScreenViewModel.searchTrigger("kevin1")
        Assert.assertEquals(homeScreenViewModel.searchResultUIState.value, SearchResultUiState.Success(userInfo, repos))
        collectJob.cancel()
    }

    @Test
    fun sateIsSuccess_withEmptySearchQuery_fail() = runTest {
        repository.addGitHubUserInfo(userInfo)
        repository.addRepos(repos)

        val collectJob = launch(UnconfinedTestDispatcher()) {
            homeScreenViewModel.searchResultUIState.collect()
        }
        homeScreenViewModel.searchTrigger("kevin1")
        Assert.assertNotEquals(homeScreenViewModel.searchResultUIState.value, SearchResultUiState.LoadFailed)
        collectJob.cancel()
    }
    @Test
    fun sateIsError_withEmptySearchQuery_success() = runTest {
        repository.addGitHubUserInfo(userInfo)
        repository.addRepos(repos)
        repository.setError(true)

        val collectJob = launch(UnconfinedTestDispatcher()) {
            homeScreenViewModel.searchResultUIState.collect()
        }
        homeScreenViewModel.searchTrigger("kevin1")
        Assert.assertEquals(homeScreenViewModel.searchResultUIState.value, SearchResultUiState.LoadFailed)
        collectJob.cancel()
    }

    @Test
    fun sateIsError_withEmptySearchQuery_fail() = runTest {
        repository.addGitHubUserInfo(userInfo)
        repository.addRepos(repos)
        repository.setError(true)

        val collectJob = launch(UnconfinedTestDispatcher()) {
            homeScreenViewModel.searchResultUIState.collect()
        }
        homeScreenViewModel.searchTrigger("kevin1")
        Assert.assertNotEquals(homeScreenViewModel.searchResultUIState.value, SearchResultUiState.Success(userInfo, repos))
        collectJob.cancel()
    }
}

private val array = Array(10) {
    GitHubRepo(id = it,
            name = "kevin$it",
            it * 10,
            "descript#$it",
            "kevin$it")
}
private val repos = array.toList()

private val userInfo = GitHubUserInfo(
        "1",
        "",
        "kevin",
        "kevin"
)
