package com.convos.core.storage.dao

import androidx.room.*
import com.convos.core.storage.entities.DBMessageEntity
import com.convos.core.storage.models.MessageContentType
import kotlinx.coroutines.flow.Flow

/**
 * DAO for message database operations
 */
@Dao
interface MessageDao {

    @Query("SELECT * FROM message WHERE id = :id")
    suspend fun getById(id: String): DBMessageEntity?

    @Query("SELECT * FROM message WHERE conversation_id = :conversationId ORDER BY date_ns DESC")
    suspend fun getByConversationId(conversationId: String): List<DBMessageEntity>

    @Query("SELECT * FROM message WHERE conversation_id = :conversationId ORDER BY date_ns DESC")
    fun observeByConversationId(conversationId: String): Flow<List<DBMessageEntity>>

    @Query("SELECT * FROM message WHERE conversation_id = :conversationId ORDER BY date_ns DESC LIMIT :limit")
    suspend fun getByConversationIdWithLimit(conversationId: String, limit: Int): List<DBMessageEntity>

    @Query("SELECT * FROM message WHERE conversation_id = :conversationId AND content_type != :excludeContentType ORDER BY date_ns DESC LIMIT 1")
    suspend fun getLastMessageByConversationId(
        conversationId: String,
        excludeContentType: MessageContentType = MessageContentType.UPDATE
    ): DBMessageEntity?

    @Query("SELECT * FROM message WHERE conversation_id = :conversationId AND content_type != :excludeContentType ORDER BY date_ns DESC LIMIT 1")
    fun observeLastMessageByConversationId(
        conversationId: String,
        excludeContentType: MessageContentType = MessageContentType.UPDATE
    ): Flow<DBMessageEntity?>

    @Query("SELECT * FROM message WHERE source_message_id = :sourceMessageId")
    suspend fun getRepliesAndReactions(sourceMessageId: String): List<DBMessageEntity>

    @Query("SELECT * FROM message WHERE client_message_id = :clientMessageId")
    suspend fun getByClientMessageId(clientMessageId: String): DBMessageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: DBMessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(messages: List<DBMessageEntity>)

    @Update
    suspend fun update(message: DBMessageEntity)

    @Delete
    suspend fun delete(message: DBMessageEntity)

    @Query("DELETE FROM message WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM message WHERE conversation_id = :conversationId")
    suspend fun deleteByConversationId(conversationId: String)

    @Query("DELETE FROM message")
    suspend fun deleteAll()
}

