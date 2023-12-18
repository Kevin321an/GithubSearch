package com.kevin.githubsearch.repodetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.githubsearch.data.GitHubInfoRepo
import com.kevin.githubsearch.data.GitHubInfoRepository
import com.kevin.githubsearch.data.model.GitHubRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface DetailUiState {
    data object LoadFailed : DetailUiState
    data object IsLoading : DetailUiState
    data class Success(
            val forkCount: Int = 0,
            val repo: GitHubRepo? = null
    ) : DetailUiState

    data object NotReady : DetailUiState
}

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModel @Inject constructor(
        repository: GitHubInfoRepo,
        savedStateHandle: SavedStateHandle) : ViewModel() {
    private val repoId = savedStateHandle.getStateFlow("repoId", "")

    val detailUiSate: StateFlow<DetailUiState> = repoId.flatMapLatest { repoId ->
        val repos = repository.getCurrentRepos()
        var forkSum = 0
        var theRepo: GitHubRepo? = null
        repos.forEach {
            forkSum += it.forks_count
            if (it.id.toString() == repoId)
                theRepo = it
        }
        if (theRepo == null) {
            flowOf(DetailUiState.LoadFailed)
        } else {
            flowOf(DetailUiState.Success(forkSum, theRepo))
        }
    }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailUiState.IsLoading
    )


}