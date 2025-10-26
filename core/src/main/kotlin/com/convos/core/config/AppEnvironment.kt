package com.convos.core.config

import android.content.Context
import java.io.File

/**
 * Application environment configuration
 * Ported from iOS AppEnvironment.swift
 */
enum class AppEnvironment(
    val rawValue: String,
    val apiBaseUrl: String,
    val databaseName: String = "convos.db",
) {
    LOCAL(
        rawValue = "local",
        apiBaseUrl = "http://localhost:3000"
    ),
    DEV(
        rawValue = "dev",
        apiBaseUrl = "https://dev-api.convos.org"
    ),
    PRODUCTION(
        rawValue = "prod",
        apiBaseUrl = "https://api.convos.org"
    );

    val isProduction: Boolean
        get() = this == PRODUCTION

    val isDevelopment: Boolean
        get() = this == DEV

    val isLocal: Boolean
        get() = this == LOCAL

    /**
     * Get the databases directory for this environment
     */
    fun getDatabasesDirectory(context: Context): File {
        return context.getDatabasePath(databaseName).parentFile
            ?: context.filesDir
    }

    /**
     * Get the database file path
     */
    fun getDatabasePath(context: Context): File {
        return context.getDatabasePath(databaseName)
    }

    /**
     * Get Firebase config resource ID based on environment
     */
    fun getFirebaseConfigResourceName(): String {
        return when (this) {
            LOCAL -> "google_services_local"
            DEV -> "google_services_dev"
            PRODUCTION -> "google_services"
        }
    }

    companion object {
        fun fromString(value: String): AppEnvironment {
            return entries.find { it.rawValue.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown environment: $value")
        }
    }
}

