package com.kevin.githubsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.kevin.githubsearch.navigation.GitHubNavHost
import com.kevin.githubsearch.ui.theme.GithubSearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubSearchTheme {
                Scaffold { padding ->
                    Surface(modifier = Modifier
                            .fillMaxSize()
                            .padding(padding), color = MaterialTheme.colorScheme.background) {
                        val navController = rememberNavController()
                        GitHubNavHost(navController)
                    }
                }
            }
        }
    }
}