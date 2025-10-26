package com.convos.core.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.convos.core.storage.models.Consent
import com.convos.core.storage.models.MemberRole
import java.util.Date

/**
 * Conversation member database entity (junction table)
 * Ported from iOS DBConversationMember.swift
 */
@Entity(
    tableName = "conversation_members",
    primaryKeys = ["conversation_id", "inbox_id"],
    foreignKeys = [
        ForeignKey(
            entity = DBConversationEntity::class,
            parentColumns = ["id"],
            childColumns = ["conversation_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["conversation_id"]),
        Index(value = ["inbox_id"]),
        Index(value = ["created_at"])
    ]
)
data class DBConversationMemberEntity(
    @ColumnInfo(name = "conversation_id")
    val conversationId: String,

    @ColumnInfo(name = "inbox_id")
    val inboxId: String,

    @ColumnInfo(name = "role")
    val role: MemberRole,

    @ColumnInfo(name = "consent")
    val consent: Consent,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date()
) {
    fun copy(
        conversationId: String = this.conversationId,
        inboxId: String = this.inboxId,
        role: MemberRole = this.role,
        consent: Consent = this.consent,
        createdAt: Date = this.createdAt
    ): DBConversationMemberEntity {
        return DBConversationMemberEntity(
            conversationId = conversationId,
            inboxId = inboxId,
            role = role,
            consent = consent,
            createdAt = createdAt
        )
    }
}

