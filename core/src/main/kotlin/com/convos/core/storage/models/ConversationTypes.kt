package com.convos.core.storage.models

/**
 * Conversation kind enum
 * Ported from iOS ConversationTypes.swift
 */
enum class ConversationKind(val value: String) {
    GROUP("group"),
    DM("dm");

    companion object {
        fun fromString(value: String): ConversationKind {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("Unknown conversation kind: $value")
        }
    }
}

/**
 * Consent status enum
 */
enum class Consent(val value: String) {
    ALLOWED("allowed"),
    DENIED("denied"),
    UNKNOWN("unknown");

    companion object {
        fun fromString(value: String): Consent {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("Unknown consent: $value")
        }
    }
}

/**
 * Member role enum
 */
enum class MemberRole(val value: String) {
    MEMBER("member"),
    ADMIN("admin"),
    SUPER_ADMIN("super_admin");

    val displayName: String
        get() = when (this) {
            MEMBER -> ""
            ADMIN -> "Admin"
            SUPER_ADMIN -> "Super Admin"
        }

    val priority: Int
        get() = when (this) {
            SUPER_ADMIN -> 1
            ADMIN -> 2
            MEMBER -> 3
        }

    companion object {
        fun fromString(value: String): MemberRole {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("Unknown member role: $value")
        }
    }
}

