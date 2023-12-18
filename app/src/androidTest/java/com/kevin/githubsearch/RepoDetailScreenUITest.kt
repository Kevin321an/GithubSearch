package com.kevin.githubsearch

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.kevin.githubsearch.data.fakeReposes
import com.kevin.githubsearch.repodetail.DetailUiState
import com.kevin.githubsearch.repodetail.RepoDetailContainer
import org.junit.Rule
import org.junit.Test

class RepoDetailScreenUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun repoNameAndDescription_Text_isDisplayed(){
        composeTestRule.setContent {
            RepoDetailContainer(
                    detailUiState = DetailUiState.Success(
                            1,
                            fakeReposes[0]
                    )
            )
        }
        composeTestRule
                .onNodeWithText("boysenberry-repo-1")
                .assertIsDisplayed()
        composeTestRule
                .onNodeWithText("Testing")
                .assertIsDisplayed()
    }

    @Test
    fun fork1_startImage_isNotDisplayed(){
        composeTestRule.setContent {
            RepoDetailContainer(
                    detailUiState = DetailUiState.Success(
                            1,
                            fakeReposes[1]
                    )
            )
        }
        composeTestRule
                .onNodeWithContentDescription("starImage")
                .assertDoesNotExist()
    }

    @Test
    fun fork5001_startImage_isDisplayed(){
        composeTestRule.setContent {
            RepoDetailContainer(
                    detailUiState = DetailUiState.Success(
                            5001,
                            fakeReposes[1]
                    )
            )
        }
        composeTestRule
                .onNodeWithContentDescription("starImage")
                .assertIsDisplayed()
    }
}