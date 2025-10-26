package com.convos.core.session

import com.convos.core.messaging.MessagingService
import com.convos.core.storage.models.Consent
import com.convos.core.storage.models.ConversationKind

/**
 * Session manager protocol
 * Ported from iOS SessionManagerProtocol.swift
 */
interface SessionManagerProtocol {

    // MARK: Inbox Management

    suspend fun addInbox(): MessagingService

    suspend fun deleteInbox(clientId: String)

    suspend fun deleteAllInboxes()

    // MARK: Messaging Services

    fun messagingService(clientId: String, inboxId: String): MessagingService?

    // MARK: Factory methods for repositories

    // TODO: Add repository factory methods as we port repositories
    // fun inviteRepository(conversationId: String): InviteRepository
    // fun conversationRepository(conversationId: String, inboxId: String, clientId: String): ConversationRepository
    // fun messagesRepository(conversationId: String): MessagesRepository
    // fun conversationsRepository(consent: List<Consent>): ConversationsRepository
    // fun conversationsCountRepo(consent: List<Consent>, kinds: List<ConversationKind>): ConversationsCountRepository

    // MARK: Notifications

    suspend fun shouldDisplayNotification(conversationId: String): Boolean

    suspend fun inboxId(conversationId: String): String?
}

