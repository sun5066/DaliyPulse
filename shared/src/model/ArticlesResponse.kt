package com.sun5066.dailypulse.model

import kotlinx.datetime.*
import kotlinx.serialization.Serializable
import kotlin.math.abs

@Serializable
data class ArticlesResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

@Serializable
data class Article(
    val author: String,
    val title: String,
    val url: String,
    val urlToImage: String? = null,
    val description: String? = null,
    private val publishedAt: String,
) {
    val date: String
        get() {
            val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
            val days = today.daysUntil(
                Instant.parse(publishedAt).toLocalDateTime(TimeZone.currentSystemDefault()).date
            ).let { abs(it) }
            
            return when {
                days > 1 -> "$days days ago"
                days == 1 -> "Yesterday"
                else -> "Today"
            }
        }
}