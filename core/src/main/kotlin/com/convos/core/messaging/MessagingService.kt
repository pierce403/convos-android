package com.convos.core.messaging

/**
 * Messaging service interface
 * Ported from iOS MessagingServiceProtocol.swift
 *
 * This is a placeholder for now. Will be implemented when we port XMTP integration.
 */
interface MessagingService {
    val clientId: String
    val inboxId: String

    suspend fun start()
    suspend fun stop()

    // TODO: Add messaging methods as we port XMTP functionality
    // suspend fun sendMessage(conversationId: String, content: String)
    // suspend fun syncConversations()
    // suspend fun syncMessages(conversationId: String)
}

