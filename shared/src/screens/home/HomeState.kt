package com.sun5066.dailypulse.screens.home

import com.sun5066.dailypulse.model.Article

data class HomeState(
    val isLoading: Boolean,
    val error: String?,
    val articles: List<Article>,
) {
    companion object {
        val initialState get() = HomeState(
            isLoading = false,
            error = null,
            articles = emptyList()
        )
    }
}

sealed interface HomeSideEffect {
    
}