package com.convos.core.domain

import java.util.Date

/**
 * Inbox domain model
 * Ported from iOS Inbox.swift
 */
data class Inbox(
    val inboxId: String,
    val clientId: String,
    val createdAt: Date = Date()
) {
    val id: String
        get() = inboxId
}

