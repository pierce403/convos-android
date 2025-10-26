package com.convos.core.auth

import com.convos.core.logging.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Identity store for managing user identities (keypairs)
 * Ported from iOS KeychainIdentityStore.swift
 */
class IdentityStore(
    private val secureStorage: SecureStorage
) {
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = false
    }

    /**
     * Save an identity to secure storage
     */
    suspend fun save(identity: KeychainIdentity) = withContext(Dispatchers.IO) {
        try {
            val key = KeychainIdentity.storageKey(identity.clientId)
            val jsonString = json.encodeToString(identity)
            secureStorage.saveString(key, jsonString)
            Logger.info("Saved identity for clientId: ${identity.clientId}")
        } catch (e: Exception) {
            Logger.error("Failed to save identity: ${identity.clientId}", e)
            throw e
        }
    }

    /**
     * Load an identity from secure storage
     */
    suspend fun load(clientId: String): KeychainIdentity? = withContext(Dispatchers.IO) {
        try {
            val key = KeychainIdentity.storageKey(clientId)
            val jsonString = secureStorage.getString(key) ?: return@withContext null
            json.decodeFromString<KeychainIdentity>(jsonString)
        } catch (e: Exception) {
            Logger.error("Failed to load identity: $clientId", e)
            null
        }
    }

    /**
     * Load all stored identities
     */
    suspend fun loadAll(): List<KeychainIdentity> = withContext(Dispatchers.IO) {
        try {
            val identities = mutableListOf<KeychainIdentity>()
            val allKeys = secureStorage.getAllKeys()

            for (key in allKeys) {
                if (key.startsWith("org.convos.identity.")) {
                    val jsonString = secureStorage.getString(key)
                    if (jsonString != null) {
                        try {
                            val identity = json.decodeFromString<KeychainIdentity>(jsonString)
                            identities.add(identity)
                        } catch (e: Exception) {
                            Logger.error("Failed to decode identity from key: $key", e)
                        }
                    }
                }
            }

            Logger.info("Loaded ${identities.size} identities from secure storage")
            identities
        } catch (e: Exception) {
            Logger.error("Failed to load all identities", e)
            emptyList()
        }
    }

    /**
     * Delete an identity from secure storage
     */
    suspend fun delete(clientId: String) = withContext(Dispatchers.IO) {
        try {
            val key = KeychainIdentity.storageKey(clientId)
            secureStorage.delete(key)
            Logger.info("Deleted identity for clientId: $clientId")
        } catch (e: Exception) {
            Logger.error("Failed to delete identity: $clientId", e)
            throw e
        }
    }

    /**
     * Delete all identities
     */
    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        try {
            val allKeys = secureStorage.getAllKeys()
            val identityKeys = allKeys.filter { it.startsWith("org.convos.identity.") }

            for (key in identityKeys) {
                secureStorage.delete(key)
            }

            Logger.info("Deleted all ${identityKeys.size} identities")
        } catch (e: Exception) {
            Logger.error("Failed to delete all identities", e)
            throw e
        }
    }

    /**
     * Check if an identity exists
     */
    suspend fun exists(clientId: String): Boolean = withContext(Dispatchers.IO) {
        val key = KeychainIdentity.storageKey(clientId)
        secureStorage.contains(key)
    }
}

