package com.sun5066.dailypulse.di

import com.sun5066.dailypulse.data.ArticleDataSource
import com.sun5066.dailypulse.data.ArticleNetworkDataSource
import com.sun5066.dailypulse.data.ArticleRepository
import com.sun5066.dailypulse.screens.home.HomeScreenModel
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val networkModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) { json(json, contentType = ContentType.Any) }
        }
    }
}

private val screenModule = module {
    factory { HomeScreenModel(get()) }
}

private val repositoryModule = module {
    factory { ArticleRepository(get()) }
}

private val dataSourceModule = module {
    factory<ArticleDataSource> { ArticleNetworkDataSource(get()) }
}

fun initKoin() {
    startKoin {
        modules(screenModule, repositoryModule, dataSourceModule, networkModule)
    }
}