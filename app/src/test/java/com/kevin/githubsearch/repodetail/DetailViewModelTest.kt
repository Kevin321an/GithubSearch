package com.kevin.githubsearch.repodetail

import MainDispatcherRule
import androidx.lifecycle.SavedStateHandle
import com.kevin.githubsearch.data.FakeGitHubInfoRepository
import com.kevin.githubsearch.data.model.GitHubRepo
import com.kevin.githubsearch.data.model.GitHubUserInfo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest{
    private lateinit var detailScreenViewModel: DetailViewModel
    private lateinit var repository: FakeGitHubInfoRepository
    private lateinit var savedStateHandle: SavedStateHandle

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        savedStateHandle = SavedStateHandle()
        repository = FakeGitHubInfoRepository()
        detailScreenViewModel = DetailViewModel(repository, savedStateHandle)
    }

    @Test
    fun reposIsSaved_withSearchQuery_fail() = runTest {
        repository.addGitHubUserInfo(userInfo)
        repository.addRepos(repos.toList())

        val collectJob = launch(UnconfinedTestDispatcher()) {
            detailScreenViewModel.detailUiSate.collect()
        }
        val saveState = SavedStateHandle()
        saveState["repoId"] = "12"
        Assert.assertEquals(detailScreenViewModel.detailUiSate.value, DetailUiState.LoadFailed)
        collectJob.cancel()
    }

    @Test
    fun reposIsSaved_withSearchQuery_success() = runTest {
        repository.addGitHubUserInfo(userInfo)
        repository.addRepos(repos.toList())
        savedStateHandle["repoId"] = "12"
        Assert.assertEquals(repository.getCurrentRepos(), repos.toList())
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