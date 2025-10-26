package com.convos.core.config

import android.content.Context
import com.convos.core.BuildConfig
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.IOException

/**
 * Manages app configuration across different environments
 * Ported from iOS ConfigManager.swift
 */
object ConfigManager {

    private lateinit var applicationContext: Context
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    /**
     * Current application environment based on build flavor
     */
    val currentEnvironment: AppEnvironment by lazy {
        val envString = BuildConfig.ENVIRONMENT
        AppEnvironment.fromString(envString)
    }

    /**
     * Initialize ConfigManager with application context
     */
    fun initialize(context: Context) {
        applicationContext = context.applicationContext
    }

    /**
     * Load configuration for current environment
     */
    fun loadConfig(): Config {
        val configFileName = when (currentEnvironment) {
            AppEnvironment.LOCAL -> "config.local.json"
            AppEnvironment.DEV -> "config.dev.json"
            AppEnvironment.PRODUCTION -> "config.prod.json"
        }

        return try {
            val configJson = applicationContext.assets.open(configFileName)
                .bufferedReader()
                .use { it.readText() }

            json.decodeFromString<Config>(configJson)
        } catch (e: IOException) {
            Timber.e(e, "Failed to load config from $configFileName")
            // Return default config
            Config(
                apiBaseUrl = currentEnvironment.apiBaseUrl,
                features = Features()
            )
        }
    }

    @Serializable
    data class Config(
        val apiBaseUrl: String,
        val features: Features = Features(),
        val logging: LoggingConfig = LoggingConfig()
    )

    @Serializable
    data class Features(
        val enableDebugMenu: Boolean = false,
        val enableExplodeFeature: Boolean = true,
        val maxConversationMembers: Int = 100
    )

    @Serializable
    data class LoggingConfig(
        val level: String = "info",
        val enableRemoteLogging: Boolean = false
    )
}

