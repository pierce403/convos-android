package com.convos.core.session

import android.content.Context
import com.convos.core.auth.IdentityStore
import com.convos.core.auth.KeychainIdentity
import com.convos.core.config.AppEnvironment
import com.convos.core.logging.Logger
import com.convos.core.messaging.MessagingService
import com.convos.core.storage.DatabaseManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.ConcurrentHashMap

/**
 * Session manager for managing multiple inbox sessions
 * Ported from iOS SessionManager.swift
 */
class SessionManager(
    private val context: Context,
    private val databaseManager: DatabaseManager,
    private val environment: AppEnvironment,
    private val identityStore: IdentityStore
) : SessionManagerProtocol {

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    // Thread-safe access to messaging services
    private val messagingServices = ConcurrentHashMap<String, MessagingService>() // Keyed by clientId

    private val _activeConversationId = MutableStateFlow<String?>(null)
    val activeConversationId: StateFlow<String?> = _activeConversationId.asStateFlow()

    init {
        scope.launch {
            initializeMessagingServices()
        }
    }

    /**
     * Initialize messaging services for all stored identities
     */
    private suspend fun initializeMessagingServices() {
        try {
            val identities = identityStore.loadAll()
            val inboxIds = identities.map { it.inboxId }
            Logger.info("Starting messaging services for inboxes: $inboxIds")

            for (identity in identities) {
                // TODO: Check if inbox is unused (UnusedInboxCache equivalent)
                // For now, start all services
                startMessagingService(identity)
            }

            Logger.info("Initialized ${messagingServices.size} messaging services")
        } catch (e: Exception) {
            Logger.error("Error starting messaging services", e)
        }
    }

    /**
     * Start a messaging service for an identity
     */
    private suspend fun startMessagingService(identity: KeychainIdentity) {
        try {
            // TODO: Create actual MessagingService instance
            // For now, create a stub
            Logger.info("Would start messaging service for inbox: ${identity.inboxId}")

            // TODO: Uncomment when MessagingService is implemented
            // val service = MessagingService(
            //     context = context,
            //     clientId = identity.clientId,
            //     inboxId = identity.inboxId,
            //     databaseManager = databaseManager,
            //     environment = environment
            // )
            // messagingServices[identity.clientId] = service
            // service.start()
        } catch (e: Exception) {
            Logger.error("Failed to start messaging service for ${identity.inboxId}", e)
        }
    }

    // MARK: - SessionManagerProtocol Implementation

    override suspend fun addInbox(): MessagingService {
        TODO("Implement inbox creation")
        // 1. Generate new keypair
        // 2. Create XMTP client
        // 3. Save identity to store
        // 4. Start messaging service
        // 5. Return messaging service
    }

    override suspend fun deleteInbox(clientId: String) {
        try {
            Logger.info("Deleting inbox for clientId: $clientId")

            // Stop and remove messaging service
            val service = messagingServices.remove(clientId)
            service?.let {
                // TODO: Stop the service
                Logger.info("Stopped messaging service for clientId: $clientId")
            }

            // Delete from database
            databaseManager.inboxDao.deleteByInboxId(clientId)

            // Delete from identity store
            identityStore.delete(clientId)

            Logger.info("Deleted inbox: $clientId")
        } catch (e: Exception) {
            Logger.error("Failed to delete inbox: $clientId", e)
            throw e
        }
    }

    override suspend fun deleteAllInboxes() {
        try {
            Logger.info("Deleting all inboxes")

            // Stop all messaging services
            messagingServices.clear()

            // Clear database
            databaseManager.clearAllData()

            // Delete all identities
            identityStore.deleteAll()

            Logger.info("Deleted all inboxes")
        } catch (e: Exception) {
            Logger.error("Failed to delete all inboxes", e)
            throw e
        }
    }

    override fun messagingService(clientId: String, inboxId: String): MessagingService? {
        return messagingServices[clientId]
    }

    override suspend fun shouldDisplayNotification(conversationId: String): Boolean {
        // Don't show notification if the conversation is currently active
        return _activeConversationId.value != conversationId
    }

    override suspend fun inboxId(conversationId: String): String? {
        return try {
            val conversation = databaseManager.conversationDao.getById(conversationId)
            conversation?.inboxId
        } catch (e: Exception) {
            Logger.error("Failed to get inboxId for conversation: $conversationId", e)
            null
        }
    }

    /**
     * Set the currently active conversation (for notification filtering)
     */
    fun setActiveConversation(conversationId: String?) {
        _activeConversationId.value = conversationId
        Logger.debug("Active conversation changed: $conversationId")
    }

    /**
     * Clean up resources
     */
    fun dispose() {
        scope.cancel()
        messagingServices.clear()
    }
}

