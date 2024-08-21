package com.sun5066.dailypulse.screens.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sun5066.dailypulse.config.ApiKeyConfig
import com.sun5066.dailypulse.data.ArticleRepository
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

class HomeScreenModel(
    private val articleRepository: ArticleRepository,
) : ScreenModel, ContainerHost<HomeState, HomeSideEffect> {
    
    override val container = screenModelScope.container<HomeState, HomeSideEffect>(HomeState.initialState)
    
    fun getArticles() = intent {
        reduce { state.copy(isLoading = true) }

        try {
            val articles = articleRepository.getArticles(
                country = "us",
                category = "business",
                apiKey = ApiKeyConfig.NEWS_API_KEY
            )
            reduce { state.copy(articles = articles, isLoading = false) }
        } catch (e: Exception) {
            reduce { state.copy(isLoading = false, error = e.message) }
        }
    }
    
    init {
        getArticles()
    }
    
}