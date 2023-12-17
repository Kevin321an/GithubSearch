package com.kevin.githubsearch.homescreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.githubsearch.data.GitHubInfoRepository
import com.kevin.githubsearch.data.Result
import com.kevin.githubsearch.data.datasource.remote.GitHubRemoteDataSource
import com.kevin.githubsearch.data.model.GitHubRepo
import com.kevin.githubsearch.data.model.GitHubUserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

sealed interface SearchResultUiState {
    data object LoadFailed : SearchResultUiState
    data object IsLoading : SearchResultUiState
    data class Success(
            val userInfo: GitHubUserInfo? = null,
            val repos: List<GitHubRepo> = emptyList()
    ) : SearchResultUiState {
        fun isHasUserInfo() = userInfo != null
        fun isEmptyReposList() = repos.isEmpty()
    }

    data object NotReady : SearchResultUiState
}

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModel @Inject constructor(
        val gitHubInfoRepository: GitHubInfoRepository,
        private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchKey = savedStateHandle.getStateFlow(key = SEARCH_KEY, initialValue = "")
    val searchDisplay = savedStateHandle.getStateFlow(key = SEARCH_DISPLAY_KEY, initialValue = "")

    val searchResultUIState: StateFlow<SearchResultUiState> =
            searchKey.flatMapLatest { key ->
                if (key.length < 2) {
                    flowOf(SearchResultUiState.NotReady)
                } else {
                    println("test315 searchResultUIState triggered")
                    gitHubInfoRepository.getGitHubUserInfo(key)
                            .zip(gitHubInfoRepository.getGitHubRepos(key)) { f1, f2 ->
                                when {
                                    (f1 is Result.Error && f2 is Result.Error) ->
                                        SearchResultUiState.LoadFailed

                                    (f1 is Result.Success && f2 is Result.Success) ->
                                        SearchResultUiState.Success(f1.data, f2.data)

                                    (f1 is Result.Success && f2 is Result.Error) -> {
                                        SearchResultUiState.Success(f1.data)
                                    }

                                    (f1 is Result.Error && f2 is Result.Success) -> {
                                        SearchResultUiState.Success(repos = f2.data)
                                    }

                                    else -> {
                                        SearchResultUiState.LoadFailed
                                    }
                                }
                            }
                }

            }.stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5_000),
                    initialValue = SearchResultUiState.IsLoading
            )

    fun updateSearchKey(key: String) {
        println("test315 updateSearchKey $key")
        savedStateHandle[SEARCH_DISPLAY_KEY] = key
    }

    fun searchTrigger(key: String) {
        savedStateHandle[SEARCH_KEY] = key
        println("test315 searchTrigger")
    }
}

private const val SEARCH_KEY = "searchKey"
private const val SEARCH_DISPLAY_KEY = "searchDisplayKey"