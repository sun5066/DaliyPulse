package com.sun5066.dailypulse.data

import com.sun5066.dailypulse.model.Article
import com.sun5066.dailypulse.model.ArticlesResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.utils.io.*

interface ArticleDataSource {
    
    suspend fun getArticles(
        country: String,
        category: String,
        apiKey: String
    ): List<Article>
    
}

class ArticleNetworkDataSource(
    private val httpClient: HttpClient
) : ArticleDataSource {
    
    override suspend fun getArticles(
        country: String,
        category: String,
        apiKey: String
    ): List<Article> {
        return try {
            val response: ArticlesResponse = httpClient
                .get("https://newsapi.org/v2/top-headlines?country=$country&category=$category&apiKey=$apiKey")
                .body()
            
            response.articles
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            
            emptyList()
        }
    }
    
}