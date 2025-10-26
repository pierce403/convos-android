package com.convos.core.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.convos.core.config.AppEnvironment
import com.convos.core.storage.converters.ConvosTypeConverters
import com.convos.core.storage.dao.*
import com.convos.core.storage.entities.*

/**
 * Main Room database for Convos
 * Ported from iOS DatabaseManager.swift
 */
@Database(
    entities = [
        DBConversationEntity::class,
        DBMessageEntity::class,
        DBInboxEntity::class,
        DBConversationMemberEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(ConvosTypeConverters::class)
abstract class ConvosDatabase : RoomDatabase() {

    abstract fun conversationDao(): ConversationDao
    abstract fun messageDao(): MessageDao
    abstract fun inboxDao(): InboxDao
    abstract fun conversationMemberDao(): ConversationMemberDao

    companion object {
        private const val DATABASE_NAME = "convos.db"

        @Volatile
        private var INSTANCE: ConvosDatabase? = null

        fun getInstance(context: Context, environment: AppEnvironment): ConvosDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = buildDatabase(context, environment)
                INSTANCE = instance
                instance
            }
        }

        private fun buildDatabase(context: Context, environment: AppEnvironment): ConvosDatabase {
            val databasePath = environment.getDatabasePath(context)

            return Room.databaseBuilder(
                context.applicationContext,
                ConvosDatabase::class.java,
                databasePath.absolutePath
            )
                .setJournalMode(JournalMode.WRITE_AHEAD_LOGGING)
                .fallbackToDestructiveMigration() // TODO: Implement proper migrations
                .build()
        }

        /**
         * Clear all data and reset the database
         */
        suspend fun clearAllData(database: ConvosDatabase) {
            database.clearAllTables()
        }
    }
}

