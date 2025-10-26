package com.convos.core.storage.dao

import androidx.room.*
import com.convos.core.storage.entities.DBConversationMemberEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for conversation member database operations
 */
@Dao
interface ConversationMemberDao {

    @Query("SELECT * FROM conversation_members WHERE conversation_id = :conversationId AND inbox_id = :inboxId")
    suspend fun get(conversationId: String, inboxId: String): DBConversationMemberEntity?

    @Query("SELECT * FROM conversation_members WHERE conversation_id = :conversationId ORDER BY created_at ASC")
    suspend fun getByConversationId(conversationId: String): List<DBConversationMemberEntity>

    @Query("SELECT * FROM conversation_members WHERE conversation_id = :conversationId ORDER BY created_at ASC")
    fun observeByConversationId(conversationId: String): Flow<List<DBConversationMemberEntity>>

    @Query("SELECT * FROM conversation_members WHERE inbox_id = :inboxId")
    suspend fun getByInboxId(inboxId: String): List<DBConversationMemberEntity>

    @Query("SELECT COUNT(*) FROM conversation_members WHERE conversation_id = :conversationId")
    suspend fun getCountByConversationId(conversationId: String): Int

    @Query("SELECT COUNT(*) FROM conversation_members WHERE conversation_id = :conversationId")
    fun observeCountByConversationId(conversationId: String): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(member: DBConversationMemberEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(members: List<DBConversationMemberEntity>)

    @Update
    suspend fun update(member: DBConversationMemberEntity)

    @Delete
    suspend fun delete(member: DBConversationMemberEntity)

    @Query("DELETE FROM conversation_members WHERE conversation_id = :conversationId AND inbox_id = :inboxId")
    suspend fun delete(conversationId: String, inboxId: String)

    @Query("DELETE FROM conversation_members WHERE conversation_id = :conversationId")
    suspend fun deleteByConversationId(conversationId: String)

    @Query("DELETE FROM conversation_members WHERE inbox_id = :inboxId")
    suspend fun deleteByInboxId(inboxId: String)

    @Query("DELETE FROM conversation_members")
    suspend fun deleteAll()
}

