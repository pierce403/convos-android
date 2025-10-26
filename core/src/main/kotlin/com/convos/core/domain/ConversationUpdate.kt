package com.convos.core.domain

import java.util.Date

/**
 * Conversation update model (for update messages)
 * Ported from iOS ConversationUpdate.swift
 */
data class ConversationUpdate(
    val initiatedBy: ConversationMember,
    val addedMembers: List<ConversationMember> = emptyList(),
    val removedMembers: List<ConversationMember> = emptyList(),
    val metadataChanges: List<MetadataChange> = emptyList(),
    val expiresAt: Date? = null
) {
    data class MetadataChange(
        val field: String,
        val oldValue: String?,
        val newValue: String?
    )

    val showsInMessagesList: Boolean
        get() = addedMembers.isNotEmpty() || removedMembers.isNotEmpty() || expiresAt != null

    val updateText: String
        get() = when {
            addedMembers.isNotEmpty() -> {
                val names = addedMembers.formattedNamesString()
                "${initiatedBy.profile.displayName} added $names"
            }
            removedMembers.isNotEmpty() -> {
                val names = removedMembers.formattedNamesString()
                "${initiatedBy.profile.displayName} removed $names"
            }
            expiresAt != null -> {
                "${initiatedBy.profile.displayName} set an expiration time"
            }
            metadataChanges.isNotEmpty() -> {
                "${initiatedBy.profile.displayName} updated the conversation"
            }
            else -> "Conversation updated"
        }
}

