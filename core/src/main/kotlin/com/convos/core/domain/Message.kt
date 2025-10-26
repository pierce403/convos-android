package com.convos.core.domain

import com.convos.core.storage.models.MessageSource
import com.convos.core.storage.models.MessageStatus
import java.util.Date

/**
 * Message type interface
 * Ported from iOS MessageType protocol
 */
interface MessageType {
    val id: String
    val conversation: Conversation
    val sender: ConversationMember
    val source: MessageSource
    val status: MessageStatus
    val content: MessageContent
    val date: Date
}

/**
 * Message content sealed class
 * Ported from iOS MessageContent enum
 */
sealed class MessageContent {
    data class Text(val text: String) : MessageContent()
    data class Emoji(val emoji: String) : MessageContent()
    data class Attachment(val url: String) : MessageContent()
    data class Attachments(val urls: List<String>) : MessageContent()
    data class Update(val update: ConversationUpdate) : MessageContent()

    val showsInMessagesList: Boolean
        get() = when (this) {
            is Update -> update.showsInMessagesList
            else -> true
        }

    val showsSender: Boolean
        get() = when (this) {
            is Update -> false
            else -> true
        }
}

/**
 * Message domain model
 * Ported from iOS Message.swift
 */
data class Message(
    override val id: String,
    override val conversation: Conversation,
    override val sender: ConversationMember,
    override val source: MessageSource,
    override val status: MessageStatus,
    override val content: MessageContent,
    override val date: Date,
    val reactions: List<MessageReaction> = emptyList()
) : MessageType

/**
 * Message reply domain model
 * Ported from iOS MessageReply.swift
 */
data class MessageReply(
    override val id: String,
    override val conversation: Conversation,
    override val sender: ConversationMember,
    override val source: MessageSource,
    override val status: MessageStatus,
    override val content: MessageContent,
    override val date: Date,
    val parentMessage: Message,
    val reactions: List<MessageReaction> = emptyList()
) : MessageType

/**
 * Message reaction domain model
 * Ported from iOS MessageReaction.swift
 */
data class MessageReaction(
    override val id: String,
    override val conversation: Conversation,
    override val sender: ConversationMember,
    override val source: MessageSource,
    override val status: MessageStatus,
    override val content: MessageContent,
    override val date: Date,
    val emoji: String
) : MessageType

/**
 * Any message sealed class (wrapper for message types)
 * Ported from iOS AnyMessage enum
 */
sealed class AnyMessage {
    data class MessageWrapper(val message: Message) : AnyMessage()
    data class ReplyWrapper(val reply: MessageReply) : AnyMessage()

    val base: MessageType
        get() = when (this) {
            is MessageWrapper -> message
            is ReplyWrapper -> reply
        }
}

