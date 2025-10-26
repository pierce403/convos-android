package com.convos.core.storage.models

/**
 * Message status enum
 * Ported from iOS DBMessage.swift
 */
enum class MessageStatus(val value: String) {
    UNPUBLISHED("unpublished"),
    PUBLISHED("published"),
    FAILED("failed"),
    UNKNOWN("unknown");

    companion object {
        fun fromString(value: String): MessageStatus {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("Unknown message status: $value")
        }
    }
}

/**
 * Message source enum
 */
enum class MessageSource(val value: String) {
    INCOMING("incoming"),
    OUTGOING("outgoing");

    val isIncoming: Boolean
        get() = this == INCOMING

    companion object {
        fun fromString(value: String): MessageSource {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("Unknown message source: $value")
        }
    }
}

/**
 * Message type enum
 */
enum class DBMessageType(val value: String) {
    ORIGINAL("original"),
    REPLY("reply"),
    REACTION("reaction");

    companion object {
        fun fromString(value: String): DBMessageType {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("Unknown message type: $value")
        }
    }
}

/**
 * Message content type enum
 */
enum class MessageContentType(val value: String) {
    TEXT("text"),
    EMOJI("emoji"),
    ATTACHMENTS("attachments"),
    UPDATE("update");

    val marksConversationAsUnread: Boolean
        get() = when (this) {
            UPDATE -> false
            else -> true
        }

    companion object {
        fun fromString(value: String): MessageContentType {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("Unknown content type: $value")
        }
    }
}

/**
 * Commit log fork status enum
 */
enum class CommitLogForkStatus(val value: String) {
    FORKED("forked"),
    NOT_FORKED("not_forked"),
    UNKNOWN("unknown");

    companion object {
        fun fromString(value: String): CommitLogForkStatus {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("Unknown fork status: $value")
        }
    }
}

