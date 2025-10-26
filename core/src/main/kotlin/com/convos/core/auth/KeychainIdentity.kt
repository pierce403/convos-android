package com.convos.core.auth

import kotlinx.serialization.Serializable

/**
 * Represents a stored identity (keypair) in secure storage
 * Ported from iOS KeychainIdentity
 */
@Serializable
data class KeychainIdentity(
    val inboxId: String,
    val clientId: String,
    val privateKeyData: String, // Base64 encoded
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
        private const val SERVICE_PREFIX = "org.convos.identity"

        fun storageKey(clientId: String): String {
            return "$SERVICE_PREFIX.$clientId"
        }
    }
}

