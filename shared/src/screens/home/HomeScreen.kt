package com.sun5066.dailypulse.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.kamel.core.utils.URI
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

data object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<HomeScreenModel>()
        val state by screenModel.container.stateFlow.collectAsState()

        val isLoading by remember(state.isLoading) { derivedStateOf { state.isLoading } }
        val error by remember(state.error) { derivedStateOf { state.error } }
        val articles by remember(state.articles) { derivedStateOf { state.articles } }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (articles.isNotEmpty()) {
            LazyColumn {
                items(
                    items = articles,
                    key = { it.hashCode() },
                    contentType = { "item" }
                ) { article ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        KamelImage(
                            modifier = Modifier
                                .fillMaxWidth(),
                            resource = asyncPainterResource(data = URI(article.urlToImage ?: "")),
                            contentDescription = article.title,
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = article.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )

                        if (article.description != null) {
                            Text(
                                text = article.description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Text(
                            text = article.date,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }

        if (error != null) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text("에러 발생")
                Text(error ?: "알 수 없음")
            }
        }
    }
}