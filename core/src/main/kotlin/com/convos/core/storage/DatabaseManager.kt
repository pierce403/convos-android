package com.convos.core.storage

import android.content.Context
import com.convos.core.config.AppEnvironment
import com.convos.core.logging.Logger
import com.convos.core.storage.dao.*

/**
 * Database manager providing access to all DAOs
 * Ported from iOS DatabaseManager.swift
 */
class DatabaseManager(
    context: Context,
    private val environment: AppEnvironment
) {
    private val database: ConvosDatabase = ConvosDatabase.getInstance(context, environment)

    val conversationDao: ConversationDao
        get() = database.conversationDao()

    val messageDao: MessageDao
        get() = database.messageDao()

    val inboxDao: InboxDao
        get() = database.inboxDao()

    val conversationMemberDao: ConversationMemberDao
        get() = database.conversationMemberDao()

    /**
     * Clear all database tables
     */
    suspend fun clearAllData() {
        try {
            Logger.info("Clearing all database data")
            ConvosDatabase.clearAllData(database)
            Logger.info("Database cleared successfully")
        } catch (e: Exception) {
            Logger.error("Failed to clear database", e)
            throw e
        }
    }

    /**
     * Run database operations in a transaction
     */
    suspend fun <R> withTransaction(block: suspend () -> R): R {
        return database.withTransaction(block)
    }

    companion object {
        @Volatile
        private var INSTANCE: DatabaseManager? = null

        fun getInstance(context: Context, environment: AppEnvironment): DatabaseManager {
            return INSTANCE ?: synchronized(this) {
                val instance = DatabaseManager(context, environment)
                INSTANCE = instance
                instance
            }
        }
    }
}

