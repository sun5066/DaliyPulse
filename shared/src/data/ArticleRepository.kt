package com.sun5066.dailypulse.data

import com.sun5066.dailypulse.model.Article

class ArticleRepository(
    private val articleDataSource: ArticleDataSource
) {
    suspend fun getArticles(
        country: String,
        category: String,
        apiKey: String
    ): List<Article> {
        return articleDataSource.getArticles(country, category, apiKey)
    }
}