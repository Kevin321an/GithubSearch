package com.kevin.githubsearch.repodetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kevin.githubsearch.ui.theme.Neutral0
import com.kevin.githubsearch.ui.theme.Neutral3
import com.kevin.githubsearch.ui.theme.Neutral6
import com.kevin.githubsearch.ui.theme.Neutral7
import com.kevin.githubsearch.ui.theme.Neutral8
import com.kevin.githubsearch.ui.theme.takeHome_gray
import com.kevin.githubsearch.ui.theme.takeHome_pink

@Composable
fun RepoDetail(
        onBackClick: () -> Unit,
        detailViewModel: DetailViewModel = hiltViewModel()
) {
    val detailUiState by detailViewModel.detailUiSate.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxWidth()) {
        Header()
        StarImageContainer(detailUiState)
        Up(onBackClick)
        Column {
            Spacer(Modifier.height(gradientHeight))
            Title(detailUiState)
            Body(detailUiState)
        }
    }
}

@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
            onClick = upPress,
            modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .size(36.dp)
                    .background(
                            color = Neutral8.copy(alpha = 0.32f),
                            shape = CircleShape
                    )
    ) {
        Icon(
                imageVector = mirroringBackIcon(),
                tint = Neutral0,
                contentDescription = "back"
        )
    }
}

@Composable
fun mirroringBackIcon() = mirroringIcon(
        ltrIcon = Icons.Outlined.ArrowBack, rtlIcon = Icons.Outlined.ArrowForward
)

@Composable
fun mirroringIcon(ltrIcon: ImageVector, rtlIcon: ImageVector): ImageVector =
        if (LocalLayoutDirection.current == LayoutDirection.Ltr) ltrIcon else rtlIcon

@Composable
private fun Header() {
    Spacer(
            modifier = Modifier
                    .height(gradientHeight)
                    .fillMaxWidth()
                    .background(Brush.horizontalGradient(listOf(takeHome_pink, takeHome_gray)))
    )
}

@Composable
fun Body(detailUiState: DetailUiState) {
    if (detailUiState is DetailUiState.Success && detailUiState.repo != null) {
        Column {
            Spacer(
                    modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
            )
            Column {
                Column {
                    Text(
                            text = "Description:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Neutral6,
                            overflow = TextOverflow.Ellipsis,
                            modifier = DetailPadding
                    )

                    Spacer(Modifier.height(40.dp))
                    Text(
                            text = detailUiState.repo.description,
//                                 text = stringResource(R.string.detail_placeholder),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = DetailPadding
                    )
                    Spacer(Modifier.height(16.dp))
                    Spacer(
                            modifier = Modifier
                                    .padding(bottom = BottomBarHeight)
                                    .navigationBarsPadding()
                                    .height(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun Title(detailUiState: DetailUiState) {
    if (detailUiState is DetailUiState.Success && detailUiState.repo != null) {
        Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                        .heightIn(min = TitleHeight)
                        .statusBarsPadding()
                        .background(color = Neutral0)
        ) {
            Spacer(Modifier.height(16.dp))
            Text(
                    text = detailUiState.repo.name,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Neutral7,
                    modifier = DetailPadding
            )

            Spacer(Modifier.height(8.dp))
            GitHubDivider()
        }
    }
}

@Composable
private fun StarImageContainer(detailUiState: DetailUiState) {
    if (detailUiState is DetailUiState.Success
            && detailUiState.forkCount > 5000) {
        Row(horizontalArrangement = Arrangement.Center,
                modifier = DetailPadding
                        .then(Modifier.statusBarsPadding())
                        .fillMaxWidth()) {
            StarImage()
        }
    }
}

@Composable
fun StarImage() {
    Image(modifier = Modifier
            .padding(10.dp)
            .size(200.dp)
            .fillMaxSize(),
            colorFilter = ColorFilter.tint(Color.Yellow),
            imageVector = Icons.Rounded.Star,
            contentScale = ContentScale.Crop,
            contentDescription = "")
}

@Composable
fun GitHubDivider(
        modifier: Modifier = Modifier,
        color: Color = Neutral3.copy(alpha = DividerAlpha),
        thickness: Dp = 1.dp
) {
    Divider(
            modifier = modifier,
            color = color,
            thickness = thickness
    )
}

private const val DividerAlpha = 0.12f
private val gradientHeight = 260.dp
private val BottomBarHeight = 56.dp
private val TitleHeight = 128.dp
private val DetailPadding = Modifier.padding(horizontal = 24.dp)