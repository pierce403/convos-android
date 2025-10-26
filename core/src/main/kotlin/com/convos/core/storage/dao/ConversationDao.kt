package com.convos.core.storage.dao

import androidx.room.*
import com.convos.core.storage.entities.DBConversationEntity
import com.convos.core.storage.models.Consent
import com.convos.core.storage.models.ConversationKind
import kotlinx.coroutines.flow.Flow

/**
 * DAO for conversation database operations
 */
@Dao
interface ConversationDao {

    @Query("SELECT * FROM conversation WHERE id = :id")
    suspend fun getById(id: String): DBConversationEntity?

    @Query("SELECT * FROM conversation WHERE id = :id")
    fun observeById(id: String): Flow<DBConversationEntity?>

    @Query("SELECT * FROM conversation WHERE inbox_id = :inboxId AND consent IN (:consents) ORDER BY created_at DESC")
    suspend fun getByInboxId(inboxId: String, consents: List<Consent>): List<DBConversationEntity>

    @Query("SELECT * FROM conversation WHERE inbox_id = :inboxId AND consent IN (:consents) ORDER BY created_at DESC")
    fun observeByInboxId(inboxId: String, consents: List<Consent>): Flow<List<DBConversationEntity>>

    @Query("SELECT * FROM conversation WHERE inbox_id = :inboxId AND kind IN (:kinds) AND consent IN (:consents) ORDER BY created_at DESC")
    fun observeByInboxIdAndKind(
        inboxId: String,
        kinds: List<ConversationKind>,
        consents: List<Consent>
    ): Flow<List<DBConversationEntity>>

    @Query("SELECT * FROM conversation WHERE client_id = :clientId")
    suspend fun getByClientId(clientId: String): List<DBConversationEntity>

    @Query("SELECT * FROM conversation WHERE client_conversation_id = :clientConversationId")
    suspend fun getByClientConversationId(clientConversationId: String): DBConversationEntity?

    @Query("SELECT COUNT(*) FROM conversation WHERE inbox_id = :inboxId AND consent IN (:consents)")
    suspend fun getCountByInboxId(inboxId: String, consents: List<Consent>): Int

    @Query("SELECT COUNT(*) FROM conversation WHERE inbox_id = :inboxId AND consent IN (:consents)")
    fun observeCountByInboxId(inboxId: String, consents: List<Consent>): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(conversation: DBConversationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(conversations: List<DBConversationEntity>)

    @Update
    suspend fun update(conversation: DBConversationEntity)

    @Delete
    suspend fun delete(conversation: DBConversationEntity)

    @Query("DELETE FROM conversation WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM conversation WHERE inbox_id = :inboxId")
    suspend fun deleteByInboxId(inboxId: String)

    @Query("DELETE FROM conversation")
    suspend fun deleteAll()
}

