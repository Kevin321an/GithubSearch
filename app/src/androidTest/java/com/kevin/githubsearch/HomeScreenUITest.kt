package com.kevin.githubsearch

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import com.kevin.githubsearch.data.fakeReposes
import com.kevin.githubsearch.data.fakeUserInfo
import com.kevin.githubsearch.homescreen.HomeScreenContent
import com.kevin.githubsearch.homescreen.SearchResultUiState
import org.junit.Rule
import org.junit.Test

class HomeScreenUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()


    @Test
    fun topBar_Text_isDisplayed(){
        composeTestRule.setContent {
            HomeScreenContent()
        }

        composeTestRule
                .onNodeWithText("Take Home")
                .assertIsDisplayed()
    }

    @Test
    fun searchBar_isFocused() {
        composeTestRule.setContent {
            HomeScreenContent()
        }
        composeTestRule
                .onNodeWithTag("searchTextField")
                .performClick()
                .assertIsFocused()
    }

    @Test
    fun searchBar_hint_isDisplayed() {
        composeTestRule.setContent {
            HomeScreenContent()
        }
        composeTestRule
                .onNodeWithText("Enter a GitHub User id")
                .assertIsDisplayed()
                .performClick()
                .assertIsDisplayed()
    }

    @Test
    fun searchBar_searchButton_isDisplayed() {
        composeTestRule.setContent {
            HomeScreenContent()
        }
        composeTestRule
                .onNodeWithText("SEARCH")
                .assertHasClickAction()
                .assertIsDisplayed()
                .performClick()
                .assertIsDisplayed()
    }

    @Test
    fun searchResult_allImageAndRepos_areVisible() {
        composeTestRule.setContent {
            HomeScreenContent(
                    searchResult = SearchResultUiState.Success(
                            userInfo = fakeUserInfo,
                            repos = fakeReposes
                    )
            )
        }
        composeTestRule
                .onNodeWithTag("githubImage")
                .assertIsDisplayed()

        val scrollable = composeTestRule
                .onAllNodes(hasScrollAction())
                .onFirst()


        fakeReposes.forEachIndexed { index, gitHubRepo ->
            scrollable.performScrollToIndex(index)
            composeTestRule
                    .onNodeWithText(gitHubRepo.name)
                    .assertIsDisplayed()
        }
    }
}