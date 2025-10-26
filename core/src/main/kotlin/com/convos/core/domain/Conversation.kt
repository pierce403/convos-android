package com.convos.core.domain

import com.convos.core.storage.models.Consent
import com.convos.core.storage.models.ConversationKind
import com.convos.core.storage.models.DebugInfo
import java.util.Date

/**
 * Conversation domain model
 * Ported from iOS Conversation.swift
 */
data class Conversation(
    val id: String,
    val inboxId: String,
    val clientId: String,
    val creator: ConversationMember,
    val createdAt: Date,
    val consent: Consent,
    val kind: ConversationKind,
    val name: String? = null,
    val description: String? = null,
    val members: List<ConversationMember> = emptyList(),
    val otherMember: ConversationMember? = null,
    val messages: List<Message> = emptyList(),
    val isPinned: Boolean = false,
    val isUnread: Boolean = false,
    val isMuted: Boolean = false,
    val lastMessage: MessagePreview? = null,
    val imageURL: String? = null,
    val isDraft: Boolean = false,
    val invite: Invite? = null,
    val debugInfo: DebugInfo = DebugInfo.EMPTY
) {
    val isForked: Boolean
        get() = debugInfo.commitLogForkStatus == com.convos.core.storage.models.CommitLogForkStatus.FORKED

    val hasJoined: Boolean
        get() = members.any { it.isCurrentUser }

    val membersWithoutCurrent: List<ConversationMember>
        get() = members.filter { !it.isCurrentUser }

    val displayName: String
        get() = if (!name.isNullOrBlank()) name else "Untitled"

    val memberNamesString: String
        get() = membersWithoutCurrent.formattedNamesString()

    val membersCountString: String
        get() {
            val totalCount = members.size
            return "$totalCount ${if (totalCount == 1) "member" else "members"}"
        }
}

