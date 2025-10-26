package com.convos.core.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.convos.core.storage.models.*
import java.util.Date
import java.util.UUID

/**
 * Conversation database entity
 * Ported from iOS DBConversation.swift
 */
@Entity(tableName = "conversation")
data class DBConversationEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "inbox_id")
    val inboxId: String,

    @ColumnInfo(name = "client_id")
    val clientId: String,

    @ColumnInfo(name = "client_conversation_id")
    val clientConversationId: String,

    @ColumnInfo(name = "invite_tag")
    val inviteTag: String,

    @ColumnInfo(name = "creator_id")
    val creatorId: String,

    @ColumnInfo(name = "kind")
    val kind: ConversationKind,

    @ColumnInfo(name = "consent")
    val consent: Consent,

    @ColumnInfo(name = "created_at")
    val createdAt: Date,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "image_url_string")
    val imageUrlString: String? = null,

    @ColumnInfo(name = "expires_at")
    val expiresAt: Date? = null,

    @ColumnInfo(name = "debug_info")
    val debugInfo: DebugInfo = DebugInfo.EMPTY
) {
    val isDraft: Boolean
        get() = isDraftId(id) && isDraftId(clientConversationId)

    val isExpired: Boolean
        get() = expiresAt?.let { it.before(Date()) } ?: false

    fun copy(
        id: String = this.id,
        inboxId: String = this.inboxId,
        clientId: String = this.clientId,
        clientConversationId: String = this.clientConversationId,
        inviteTag: String = this.inviteTag,
        creatorId: String = this.creatorId,
        kind: ConversationKind = this.kind,
        consent: Consent = this.consent,
        createdAt: Date = this.createdAt,
        name: String? = this.name,
        description: String? = this.description,
        imageUrlString: String? = this.imageUrlString,
        expiresAt: Date? = this.expiresAt,
        debugInfo: DebugInfo = this.debugInfo
    ): DBConversationEntity {
        return DBConversationEntity(
            id = id,
            inboxId = inboxId,
            clientId = clientId,
            clientConversationId = clientConversationId,
            inviteTag = inviteTag,
            creatorId = creatorId,
            kind = kind,
            consent = consent,
            createdAt = createdAt,
            name = name,
            description = description,
            imageUrlString = imageUrlString,
            expiresAt = expiresAt,
            debugInfo = debugInfo
        )
    }

    companion object {
        private const val DRAFT_PREFIX = "draft-"

        fun generateDraftConversationId(): String {
            return "$DRAFT_PREFIX${UUID.randomUUID()}"
        }

        fun isDraftId(id: String): Boolean {
            return id.startsWith(DRAFT_PREFIX)
        }
    }
}

