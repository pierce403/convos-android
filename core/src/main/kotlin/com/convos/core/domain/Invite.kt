package com.convos.core.domain

import java.util.Date

/**
 * Invite domain model
 * Ported from iOS Invite.swift
 */
data class Invite(
    val id: String,
    val conversationId: String,
    val creatorInboxId: String,
    val inviteTag: String,
    val createdAt: Date,
    val expiresAt: Date? = null
) {
    val isExpired: Boolean
        get() = expiresAt?.let { it.before(Date()) } ?: false
}

