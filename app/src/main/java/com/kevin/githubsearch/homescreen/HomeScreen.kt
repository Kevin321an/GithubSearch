package com.kevin.githubsearch.homescreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.kevin.githubsearch.data.model.GitHubRepo
import com.kevin.githubsearch.data.model.GitHubUserInfo
import com.kevin.githubsearch.ui.theme.Elevations
import com.kevin.githubsearch.ui.theme.GithubSearchTheme
import com.kevin.githubsearch.ui.theme.takeHome_dark_blue1
import com.kevin.githubsearch.ui.theme.takeHome_pink
import com.kevin.githubsearch.ui.theme.takeHome_white

@Composable
fun HomeScreen(
        modifier: Modifier = Modifier,
        homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
        onRepoClick: (String) -> Unit
) {
    val searchText by homeScreenViewModel.searchDisplay.collectAsStateWithLifecycle()
    val searchResult by homeScreenViewModel.searchResultUIState.collectAsStateWithLifecycle()

    Column() {
        TopAppBar()
        Spacer(modifier = Modifier.height(12.dp))
        Searchbar(
                searchText = searchText,
                onSearchChange = homeScreenViewModel::updateSearchKey,
                searchTrigger = homeScreenViewModel::searchTrigger,
        )
        SearchBody(searchResult, onRepoClick = onRepoClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    TopAppBar(title = { Text(text = "Take Home") },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = takeHome_dark_blue1,
                    titleContentColor = takeHome_white
            ))
}

@Composable
fun Searchbar(searchText: String,
        modifier: Modifier = Modifier,
        onSearchChange: (String) -> Unit,
        searchTrigger: (String) -> Unit

) {

    Row(horizontalArrangement = Arrangement.Center,
            modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)) {
        SearchTextField(searchText, onSearchChange = onSearchChange)
        ElevatedButton(onClick = { searchTrigger(searchText) }) {
            Text(text = "SEARCH")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(searchText: String, onSearchChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchTriggered = {
        keyboardController?.hide()
        onSearchChange(searchText)
    }

    TextField(
            value = searchText,
            onValueChange = {
                if (!it.contains("\n")) {
                    onSearchChange(it)
                }
            },
            colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = takeHome_pink,
                    unfocusedContainerColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    cursorColor = takeHome_pink
            ),
            maxLines = 1,
            singleLine = true,
            label = { Text(text = "Enter a GitHub User id") },
            keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchTriggered()
                    },
            )
    )
}

@Composable
fun SearchBody(searchResult: SearchResultUiState,
        modifier: Modifier = Modifier,
        onRepoClick: (String) -> Unit
) {

    Column(modifier.fillMaxWidth()) {
        when (searchResult) {
            SearchResultUiState.LoadFailed,
            SearchResultUiState.IsLoading,
            -> Unit

            is SearchResultUiState.Success -> {
                if (searchResult.isHasUserInfo())
                    GitHubImage(searchResult.userInfo)

                if (!searchResult.isEmptyReposList()) {
                    Repos(searchResult.repos, onSelectRepo = onRepoClick)
                }
            }

            else -> {
                Unit
            }
        }
    }
}

@Composable
fun GitHubImage(userInfo: GitHubUserInfo?) {
    Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)) {
        userInfo?.image_url?.let {
            AsyncImage(
                    model = userInfo.image_url,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                            .padding(10.dp)
                            .size(200.dp)

            )
        }
    }
}

@Composable
fun Repos(
        repos: List<GitHubRepo>,
        modifier: Modifier = Modifier,
        onSelectRepo: (String) -> Unit = {},
) {
    LazyColumn(modifier) {
        item {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        }
        itemsIndexed(
                items = repos,
                key = { index, item -> item.id + index }
        ) { index, item ->
            MyRepo(
                    index,
                    item,
                    { onSelectRepo(item.id.toString()) }
            )

        }
    }
}

@Composable
fun MyRepo(index: Int,
        repo: GitHubRepo,
        onSelectRepo: () -> Unit,
        modifier: Modifier = Modifier) {
    Card(
            modifier = Modifier
                    .padding(vertical = 4.dp,
                            horizontal = 8.dp)
                    .fillMaxWidth()
                    .clickable(onClick = onSelectRepo),
            elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.dp
            ),
//            shape =RectangleShape,
            shape = RoundedCornerShape(3.dp),
//            border = BorderStroke(0.1.dp, Color.Transparent),
            colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
//                    containerColor = Color.Transparent,
            )
    ) {

        Column(modifier = modifier
                .padding(10.dp)
        ) {
            Row {
                Text(text = repo.name,
                        modifier = Modifier.padding(bottom = 2.dp),
                        fontWeight = FontWeight.Bold)
            }

            Row {
                Text(text = repo.description)
            }

        }

    }
}

@Preview
@Composable
private fun SearchbarPreview() {
    GithubSearchTheme {
        Searchbar("", onSearchChange = {}, searchTrigger = {})
    }
}

@Preview
@Composable
private fun TopAppBarPreview() {
    GithubSearchTheme {
        TopAppBar()
    }
}

@Preview
@Composable
private fun MyRepoPreview() {
    MyRepo(1,
            GitHubRepo(1,
                    "repo Name",
                    10,
                    "this is a repo description",
                    ""),
            {}
    )
}
