package com.convos.core.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.convos.core.storage.models.*
import java.util.Date

/**
 * Message database entity
 * Ported from iOS DBMessage.swift
 */
@Entity(
    tableName = "message",
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
        Index(value = ["sender_id"]),
        Index(value = ["date_ns"]),
        Index(value = ["source_message_id"])
    ]
)
data class DBMessageEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "client_message_id")
    val clientMessageId: String,

    @ColumnInfo(name = "conversation_id")
    val conversationId: String,

    @ColumnInfo(name = "sender_id")
    val senderId: String,

    @ColumnInfo(name = "date_ns")
    val dateNs: Long,

    @ColumnInfo(name = "date")
    val date: Date,

    @ColumnInfo(name = "status")
    val status: MessageStatus,

    @ColumnInfo(name = "message_type")
    val messageType: DBMessageType,

    @ColumnInfo(name = "content_type")
    val contentType: MessageContentType,

    @ColumnInfo(name = "text")
    val text: String? = null,

    @ColumnInfo(name = "emoji")
    val emoji: String? = null,

    @ColumnInfo(name = "source_message_id")
    val sourceMessageId: String? = null,

    @ColumnInfo(name = "attachment_urls")
    val attachmentUrls: List<String> = emptyList(),

    @ColumnInfo(name = "update")
    val update: MessageUpdate? = null
) {
    val attachmentUrl: String?
        get() = attachmentUrls.firstOrNull()

    fun copy(
        id: String = this.id,
        clientMessageId: String = this.clientMessageId,
        conversationId: String = this.conversationId,
        senderId: String = this.senderId,
        dateNs: Long = this.dateNs,
        date: Date = this.date,
        status: MessageStatus = this.status,
        messageType: DBMessageType = this.messageType,
        contentType: MessageContentType = this.contentType,
        text: String? = this.text,
        emoji: String? = this.emoji,
        sourceMessageId: String? = this.sourceMessageId,
        attachmentUrls: List<String> = this.attachmentUrls,
        update: MessageUpdate? = this.update
    ): DBMessageEntity {
        return DBMessageEntity(
            id = id,
            clientMessageId = clientMessageId,
            conversationId = conversationId,
            senderId = senderId,
            dateNs = dateNs,
            date = date,
            status = status,
            messageType = messageType,
            contentType = contentType,
            text = text,
            emoji = emoji,
            sourceMessageId = sourceMessageId,
            attachmentUrls = attachmentUrls,
            update = update
        )
    }
}

