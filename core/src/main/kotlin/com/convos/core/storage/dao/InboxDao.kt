package com.convos.core.storage.dao

import androidx.room.*
import com.convos.core.storage.entities.DBInboxEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for inbox database operations
 */
@Dao
interface InboxDao {

    @Query("SELECT * FROM inbox WHERE inbox_id = :inboxId")
    suspend fun getById(inboxId: String): DBInboxEntity?

    @Query("SELECT * FROM inbox WHERE inbox_id = :inboxId")
    fun observeById(inboxId: String): Flow<DBInboxEntity?>

    @Query("SELECT * FROM inbox WHERE client_id = :clientId")
    suspend fun getByClientId(clientId: String): List<DBInboxEntity>

    @Query("SELECT * FROM inbox WHERE client_id = :clientId")
    fun observeByClientId(clientId: String): Flow<List<DBInboxEntity>>

    @Query("SELECT * FROM inbox ORDER BY created_at DESC")
    suspend fun getAll(): List<DBInboxEntity>

    @Query("SELECT * FROM inbox ORDER BY created_at DESC")
    fun observeAll(): Flow<List<DBInboxEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(inbox: DBInboxEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(inboxes: List<DBInboxEntity>)

    @Delete
    suspend fun delete(inbox: DBInboxEntity)

    @Query("DELETE FROM inbox WHERE inbox_id = :inboxId")
    suspend fun deleteById(inboxId: String)

    @Query("DELETE FROM inbox")
    suspend fun deleteAll()
}

