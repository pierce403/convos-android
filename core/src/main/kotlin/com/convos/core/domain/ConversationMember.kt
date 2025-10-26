package com.convos.core.domain

import com.convos.core.storage.models.MemberRole

/**
 * Conversation member domain model
 * Ported from iOS ConversationMember.swift
 */
data class ConversationMember(
    val profile: Profile,
    val role: MemberRole,
    val isCurrentUser: Boolean
) {
    val id: String
        get() = profile.inboxId
}

/**
 * Format a list of conversation members into a human-readable string
 */
fun List<ConversationMember>.formattedNamesString(): String {
    return this.map { it.profile }.formattedNamesString()
}

/**
 * Sort members by role and name
 */
fun List<ConversationMember>.sortedByRole(): List<ConversationMember> {
    return this.sortedWith { member1, member2 ->
        // Show current user first
        when {
            member1.isCurrentUser -> -1
            member2.isCurrentUser -> 1
            else -> {
                // Sort by role hierarchy: superAdmin > admin > member
                val priority1 = member1.role.priority
                val priority2 = member2.role.priority

                when {
                    priority1 != priority2 -> priority1.compareTo(priority2)
                    // Same role, sort alphabetically by name
                    else -> member1.profile.displayName.compareTo(member2.profile.displayName)
                }
            }
        }
    }
}

