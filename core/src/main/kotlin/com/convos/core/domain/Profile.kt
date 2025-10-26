package com.convos.core.domain

/**
 * User profile domain model
 * Ported from iOS Profile.swift
 */
data class Profile(
    val inboxId: String,
    val name: String? = null,
    val avatar: String? = null
) {
    val displayName: String
        get() = name ?: "Someone"

    val avatarURL: String?
        get() = avatar?.takeIf { it.isNotBlank() }

    companion object {
        fun empty(inboxId: String = ""): Profile {
            return Profile(
                inboxId = inboxId,
                name = null,
                avatar = null
            )
        }

        fun mock(inboxId: String = "", name: String = "Jane Doe"): Profile {
            return Profile(
                inboxId = inboxId,
                name = name,
                avatar = "https://example.com/avatar.jpg"
            )
        }
    }
}

/**
 * Format a list of profiles into a human-readable string
 */
fun List<Profile>.formattedNamesString(): String {
    val displayNames = this.map { it.displayName }
        .filter { it.isNotBlank() }
        .sorted()

    return when (displayNames.size) {
        0 -> ""
        1 -> displayNames[0]
        2 -> displayNames.joinToString(" & ")
        else -> {
            val allButLast = displayNames.dropLast(1).joinToString(", ")
            val last = displayNames.lastOrNull() ?: ""
            "$allButLast and $last"
        }
    }
}

