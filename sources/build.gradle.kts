import java.util.*

val Project.localProperties: Properties
    get() = Properties().apply {
        val localPropertiesFile = rootProject.file("local.properties")

        if (localPropertiesFile.exists()) {
            this.load(localPropertiesFile.inputStream())
        }
    }


tasks.register("generateApiKey") {
    doLast {
        val apiKey = localProperties["news_api_key"]?.toString() ?: "No API Key"
        
        val outputDir = file("${rootProject.projectDir}/shared/src/config")
        val apiKeyFile = File(outputDir, "ApiKeyConfig.kt")

        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }

        apiKeyFile.writeText("""
            package com.sun5066.dailypulse.config
            
            object ApiKeyConfig {
                const val NEWS_API_KEY = "$apiKey"
            }
        """.trimIndent())
        
        println("ApiKeysConfig.kt 파일이 ${apiKeyFile.absolutePath}에 생성되었습니다.")
    }
}